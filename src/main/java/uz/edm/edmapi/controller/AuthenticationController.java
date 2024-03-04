package uz.edm.edmapi.controller;

import com.edm.api.AuthenticationApi;
import com.edm.model.dto.AuthenticationToken;
import com.edm.model.dto.UserCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import uz.edm.edmapi.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @CrossOrigin
    @Override
    public ResponseEntity<AuthenticationToken> login(UserCredentials userCredentials) {

        log.info("Handling login request for credentials {}", userCredentials);

        String jwt = authenticationService.getTokenFromIndexer(userCredentials);

        if (!jwt.isBlank()) {
            return ResponseEntity.ok(authenticationService.getAuthenticationToken(userCredentials));
        } else {
            return ResponseEntity.status(401).body(new AuthenticationToken());
        }

    }
}
