package com.pm.patient_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pm.patient_service.dto.PatientRequestDto;
import com.pm.patient_service.dto.PatientResponseDto;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients(){
       List<Patient> patients = patientRepository.findAll();

       return patients.stream().
       map(PatientMapper::toDto).toList();
    }


    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto){

        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExistsException("A patient with email " + patientRequestDto.getEmail() + " already exists.");
        }

        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDto));
        return PatientMapper.toDto(patient);
    }


    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto){

        Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new PatientNotFoundException("Patient not found with id: " + id)
        );

        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)){
            throw new EmailAlreadyExistsException("A patient with email " + patientRequestDto.getEmail() + " already exists.");
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        patient.setAddress(patientRequestDto.getAddress());

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);


    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(
            () -> new PatientNotFoundException("Patient not found with id: " + id)
        );
        patientRepository.deleteById(patient.getId());
        // patientRepository.deleteById(id);
    }


}
