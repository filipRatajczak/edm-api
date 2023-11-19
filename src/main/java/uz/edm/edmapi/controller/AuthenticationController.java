package uz.edm.edmapi.controller;

import com.edm.api.AuthenticationApi;
import com.edm.model.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import uz.edm.edmapi.security.EmployeeDetails;
import uz.edm.edmapi.security.JwtUtilities;
import uz.edm.edmapi.service.AuthenticationService;
import com.edm.model.dto.AuthenticationToken;
import com.edm.model.dto.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import uz.edm.edmapi.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController implements AuthenticationApi {

    private final JwtUtilities jwtUtilities;
    private final AuthenticationService authenticationService;
    private final EmployeeService employeeService;

    @CrossOrigin
    @Override
    public ResponseEntity<AuthenticationToken> login(UserCredentials userCredentials) {

        AuthenticationToken token = new AuthenticationToken();

        String token1 = authenticationService.getToken(userCredentials);

        if (!token1.isBlank()) {
            EmployeeDto employeeByEmail = employeeService.getEmployeeByEmail(userCredentials.getUsername()).get();
            token.setLogin(userCredentials.getUsername());
            token.setToken(jwtUtilities.generateToken(userCredentials.getUsername(), List.of(employeeByEmail.getRole().name())));
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body(token);
        }

    }
}
