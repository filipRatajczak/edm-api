package uz.edm.edmapi.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.edm.grpc.authentication.AuthenticationServiceGrpc;
import uz.edm.grpc.disposition.DispositionServiceGrpc;
import uz.edm.grpc.employee.EmployeeServiceGrpc;
import uz.edm.grpc.timeentry.TimeEntryServiceGrpc;

@Slf4j
@Configuration
public class GrpcClient {
    @Value("${grpc.indexer.host}")
    private String indexerHost;

    @Value("${grpc.indexer.port}")
    private Integer indexerGrpcPort;

    private final JwtTokenSender jwtTokenSender = new JwtTokenSender();


    @Bean
    ManagedChannel indexerChannel() {
        return ManagedChannelBuilder.forAddress(indexerHost, indexerGrpcPort).usePlaintext().build();
    }

    @Bean
    DispositionServiceGrpc.DispositionServiceBlockingStub dispositionServiceBlockingStub() {
        return DispositionServiceGrpc.newBlockingStub(indexerChannel()).withCallCredentials(jwtTokenSender);
    }

    @Bean
    TimeEntryServiceGrpc.TimeEntryServiceBlockingStub timeEntryServiceBlockingStub() {
        return TimeEntryServiceGrpc.newBlockingStub(indexerChannel()).withCallCredentials(jwtTokenSender);
    }

    @Bean
    EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeServiceBlockingStub() {
        return EmployeeServiceGrpc.newBlockingStub(indexerChannel()).withCallCredentials(jwtTokenSender);
    }

    @Bean
    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub() {
        return AuthenticationServiceGrpc.newBlockingStub(indexerChannel());
    }

}