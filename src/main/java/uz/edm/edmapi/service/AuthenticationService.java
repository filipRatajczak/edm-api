package uz.edm.edmapi.service;


import com.edm.model.dto.AuthenticationToken;
import com.edm.model.dto.EmployeeDto;
import com.edm.model.dto.UserCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.edm.edmapi.security.JwtUtilities;
import uz.edm.grpc.authentication.AuthenticationServiceGrpc;
import uz.edm.grpc.authentication.LoginRequest;
import uz.edm.grpc.authentication.LoginResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authenticationServiceBlockingStub;
    private final JwtUtilities jwtUtilities;
    private final EmployeeService employeeService;

    public String getTokenFromIndexer(UserCredentials userCredentials) {
        LoginResponse response = authenticationServiceBlockingStub.login(
                LoginRequest.newBuilder()
                        .setEmail(userCredentials.getEmail())
                        .setPassword(userCredentials.getPassword())
                        .build());
        return response.getToken();
    }

    public AuthenticationToken getAuthenticationToken(UserCredentials userCredentials) {
        return employeeService.getEmployeeByEmail(userCredentials.getEmail()).map(user -> {
            AuthenticationToken token = new AuthenticationToken();
            token.setEmail(user.getEmail());
            token.setEmployeeCode(user.getEmployeeCode());
            token.setToken(getTokenForUser(userCredentials, user));
            token.setRole(user.getRole().name());
            return token;
        }).orElse(null);
    }

    public String getTokenForUser(UserCredentials userCredentials, EmployeeDto foundUser) {
        return jwtUtilities.generateToken(userCredentials.getEmail(), List.of(foundUser.getRole().name()));
    }


}
