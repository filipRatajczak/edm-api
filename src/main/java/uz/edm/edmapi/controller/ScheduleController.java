package uz.edm.edmapi.controller;

import com.edm.api.ScheduleApi;
import com.edm.model.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.edm.edmapi.client.CoreClient;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ScheduleController implements ScheduleApi {

    private final CoreClient coreClient;

    @Override
    public ResponseEntity<List<ScheduleDto>> getScheduleInPeriod(LocalDate from, LocalDate to) {
        log.info("invoked get schedule");
        return ResponseEntity.ok(coreClient.getSchedule(from, to));
    }

    @Override
    public ResponseEntity<List<ScheduleDto>> postScheduleInPeriod(LocalDate from, LocalDate to) {
        return ResponseEntity.ok(coreClient.postSchedule(from, to));
    }
}
