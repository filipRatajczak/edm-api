package uz.edm.edmapi;

import com.edm.model.dto.UserCredentials;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.edm.edmapi.constants.Credentials;
import uz.edm.edmapi.service.AuthenticationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class Register implements InitializingBean {

    private final AuthenticationService authenticationService;

    @Value("${edm.api.credentials.username}")
    private String username;
    @Value("${edm.api.credentials.password}")
    private String password;

    @Override
    public void afterPropertiesSet() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(username);
        userCredentials.setPassword(password);
        Credentials.jwtToken = authenticationService.getToken(userCredentials);
        log.info("Successfully retrieved JWT: {}", Credentials.jwtToken);
    }
}
