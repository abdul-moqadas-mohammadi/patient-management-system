package com.pm.billing_service.grpc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    public static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                              StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("Received gRPC request to create billing account for ID: {}", billingRequest.getPatientId().toString());
            // Business logic to create a billing account would go here

            BillingResponse billingResponse = BillingResponse.newBuilder().
                    setAccountId("12345").
                    setStatus("ACTIVE").build();

                    responseObserver.onNext(billingResponse);
                    responseObserver.onCompleted();
        
    }

}
