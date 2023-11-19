package uz.edm.edmapi.service;


import com.edm.model.dto.UserCredentials;
import uz.edm.grpc.authentication.AuthenticationServiceGrpc;
import uz.edm.grpc.authentication.LoginRequest;
import uz.edm.grpc.authentication.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;

    public String getToken(UserCredentials userCredentials) {
        LoginResponse response = authenticationServiceBlockingStub.login(
                LoginRequest.newBuilder()
                        .setEmail(userCredentials.getUsername())
                        .setPassword(userCredentials.getPassword())
                        .build());
        return response.getToken();
    }


}
