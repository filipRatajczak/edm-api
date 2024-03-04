package uz.edm.edmapi.register;


import com.edm.model.dto.AuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.edm.edmapi.client.CoreClient;
import uz.edm.edmapi.model.CoreAuthTokenHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoreAuthTokenScheduler {

    private final CoreClient coreClient;

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 5)
    public void fetchAuthToken() {
        log.info("Sending request for token to core. Next request will be sent in 5 minutes.");
        AuthenticationToken authToken = coreClient.getAuthToken();
        CoreAuthTokenHolder.jwtToken = authToken.getToken();
        log.info("Token successfully retrieved. Next request will be sent in 5 minutes. Token:[{}]", CoreAuthTokenHolder.jwtToken);
    }

}
