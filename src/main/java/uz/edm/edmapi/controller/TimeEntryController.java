package uz.edm.edmapi.controller;

import com.edm.api.TimeEntriesApi;
import uz.edm.edmapi.service.TimeEntryService;
import com.edm.model.dto.TimeEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TimeEntryController implements TimeEntriesApi {

    private final TimeEntryService timeEntryService;

    @Override
    public ResponseEntity<Void> deleteTimeEntry(String employeeCode) {
        timeEntryService.deleteTimeEntry(employeeCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<TimeEntryDto>> getTimeEntriesByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        List<TimeEntryDto> result = timeEntryService.getTimeEntriesByEmployeeCode(employeeCode, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<List<TimeEntryDto>> timeEntriesGet() {
        List<TimeEntryDto> result = timeEntryService.timeEntriesGet();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<TimeEntryDto> timeEntriesPost(TimeEntryDto timeEntryDto) {
        TimeEntryDto result = timeEntryService.timeEntriesPost(timeEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<TimeEntryDto> updateTimeEntry(String employeeCode, TimeEntryDto timeEntryDto) {
        TimeEntryDto result = timeEntryService.updateTimeEntry(employeeCode, timeEntryDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
