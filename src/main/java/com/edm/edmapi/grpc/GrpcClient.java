package com.edm.edmapi.grpc;


import gft.edm.grpc.disposition.DispositionServiceGrpc;
import gft.edm.grpc.disposition.EmployeeServiceGrpc;
import gft.edm.grpc.disposition.TimeEntryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClient {
    @Value("${grpc.indexer.host}")
    private String indexerHost;

    @Value("${grpc.indexer.port}")
    private Integer indexerGrpcPort;

    @Bean
    ManagedChannel indexerChannel() {
        return ManagedChannelBuilder.forAddress(indexerHost, indexerGrpcPort).usePlaintext().build();
    }

    @Bean
    DispositionServiceGrpc.DispositionServiceBlockingStub dispositionServiceBlockingStub() {
        return DispositionServiceGrpc.newBlockingStub(indexerChannel());
    }

    @Bean
    TimeEntryServiceGrpc.TimeEntryServiceBlockingStub timeEntryServiceBlockingStub() {
        return TimeEntryServiceGrpc.newBlockingStub(indexerChannel());
    }

    @Bean
    EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub() {
        return EmployeeServiceGrpc.newBlockingStub(indexerChannel());
    }

}