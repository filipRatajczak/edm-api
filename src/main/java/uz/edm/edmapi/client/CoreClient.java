package uz.edm.edmapi.client;


import com.edm.model.dto.AuthenticationToken;
import com.edm.model.dto.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import uz.edm.edmapi.model.CoreApplicationCredentials;
import uz.edm.edmapi.model.CoreAuthTokenHolder;

import java.time.LocalDate;
import java.util.List;

import static uz.edm.edmapi.constants.Constants.CONST_AUTHORIZATION;
import static uz.edm.edmapi.constants.Constants.CONST_BEARER;
import static uz.edm.edmapi.constants.Constants.CONST_LOGIN_PATH;
import static uz.edm.edmapi.constants.Constants.CONST_SCHEDULE_PATH;


@Slf4j
@Service
public class CoreClient {

    private final WebClient webClient;
    private final CoreApplicationCredentials coreApplicationCredentials;

    public CoreClient(@Value("${edm.core.url}") String apiUrl, CoreApplicationCredentials coreApplicationCredentials) {
        this.webClient = WebClient.create(apiUrl);
        this.coreApplicationCredentials = coreApplicationCredentials;
    }

    public AuthenticationToken getAuthToken() {

        String uri = UriComponentsBuilder.newInstance().path(CONST_LOGIN_PATH).toUriString();

        Mono<CoreApplicationCredentials> userCredentialsMono = Mono.just(coreApplicationCredentials);

        Mono<AuthenticationToken> disposition = webClient.post()
                .uri(uri)
                .body(userCredentialsMono, CoreApplicationCredentials.class)
                .retrieve()
                .bodyToMono(AuthenticationToken.class);

        return disposition.block();
    }

    public List<ScheduleDto> getSchedule(LocalDate from, LocalDate to) {

        String uri = UriComponentsBuilder.newInstance().path(CONST_SCHEDULE_PATH).query("from={from}").query("to={to}").buildAndExpand(from.toString(), to.toString()).toString();

        Mono<List<ScheduleDto>> disposition = webClient.get()
                .uri(uri)
                .header(CONST_AUTHORIZATION, CONST_BEARER + CoreAuthTokenHolder.jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        return disposition.block();
    }

    public List<ScheduleDto> postSchedule(LocalDate from, LocalDate to) {

        String uri = UriComponentsBuilder.newInstance().path(CONST_SCHEDULE_PATH).query("from={from}").query("to={to}").buildAndExpand(from.toString(), to.toString()).toString();

        Mono<List<ScheduleDto>> disposition = webClient.post()
                .uri(uri)
                .header(CONST_AUTHORIZATION, CONST_BEARER + CoreAuthTokenHolder.jwtToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        return disposition.block();
    }


}
