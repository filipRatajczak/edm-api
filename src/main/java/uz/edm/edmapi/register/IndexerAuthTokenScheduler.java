package uz.edm.edmapi.register;

import com.edm.model.dto.UserCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.edm.edmapi.model.IndexerAuthTokenHolder;
import uz.edm.edmapi.service.AuthenticationService;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class IndexerAuthTokenScheduler {

    private final AuthenticationService authenticationService;

    @Value("${edm.api.credentials.email}")
    private String email;
    @Value("${edm.api.credentials.password}")
    private String password;


    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 5)
    public void fetchAuthToken() {
        log.info("Sending request for token to indexer. Next request will be sent in 5 minutes.");
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(email);
        userCredentials.setPassword(password);
        IndexerAuthTokenHolder.jwtToken = authenticationService.getTokenFromIndexer(userCredentials);
        log.info("Token successfully retrieved. Next request will be sent in 5 minutes. Token:[{}]", IndexerAuthTokenHolder.jwtToken);
    }
}
