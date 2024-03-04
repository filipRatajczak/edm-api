package uz.edm.edmapi.service;

import com.edm.model.dto.TimeEntryDto;
import com.google.protobuf.Empty;

import uz.edm.grpc.timeentry.TimeEntryServiceGrpc;
import uz.edm.grpc.timeentry.CreateTimeEntryRequest;
import uz.edm.grpc.timeentry.DeleteTimeEntryRequest;
import uz.edm.grpc.timeentry.GetTimeEntryByEmployeeCodeRequest;
import uz.edm.grpc.timeentry.TimeEntries;
import uz.edm.grpc.timeentry.TimeEntry;
import uz.edm.grpc.timeentry.UpdateTimeEntryRequest;
import lombok.RequiredArgsConstructor;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TimeEntryService {

    private final TimeEntryServiceGrpc.TimeEntryServiceBlockingStub timeEntryServiceBlockingStub;

    public void deleteTimeEntry(String employeeCode) {
        timeEntryServiceBlockingStub.deleteTimeEntry(DeleteTimeEntryRequest.newBuilder().setId(employeeCode).build());
    }

    public List<TimeEntryDto> getTimeEntriesByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        TimeEntries timeEntries = timeEntryServiceBlockingStub.getTimeEntryByEmployeeCode(GetTimeEntryByEmployeeCodeRequest.newBuilder().setEmployeeCode(employeeCode).setStart(from.toString()).setStop(to.toString()).build());
        return mapTimeEntriesToDto(timeEntries);
    }

    public List<TimeEntryDto> timeEntriesGet() {
        TimeEntries timeEntries = timeEntryServiceBlockingStub.getAllTimeEntries(Empty.getDefaultInstance());
        return mapTimeEntriesToDto(timeEntries);
    }

    public TimeEntryDto timeEntriesPost(TimeEntryDto timeEntryDto) {
        TimeEntry timeEntry = timeEntryServiceBlockingStub.createTimeEntry(CreateTimeEntryRequest.newBuilder().setTimeEntry(timeEntryDtoToGrpcFormat(timeEntryDto)).build());
        return mapTimeEntryToDto(timeEntry);
    }

    public TimeEntryDto updateTimeEntry(String employeeCode, TimeEntryDto timeEntryDto) {
        TimeEntry timeEntry = timeEntryServiceBlockingStub.updateTimeEntry(UpdateTimeEntryRequest.newBuilder().setEmployeeCode(employeeCode).setTimeEntry(timeEntryDtoToGrpcFormat(timeEntryDto)).build());
        return mapTimeEntryToDto(timeEntry);
    }

    private TimeEntries timeEntriesDtoToGrpcFormat(List<TimeEntryDto> timeEntryDtos) {
        List<TimeEntry> collect = timeEntryDtos.stream()
                .map(this::timeEntryDtoToGrpcFormat)
                .toList();
        return TimeEntries.newBuilder().addAllTimeEntry(collect).build();
    }

    private TimeEntry timeEntryDtoToGrpcFormat(TimeEntryDto timeEntryDto) {
        return TimeEntry.newBuilder()
                .setDay(timeEntryDto.getDay().toString())
                .setStart(timeEntryDto.getStart())
                .setStop(timeEntryDto.getStop())
                .setEmployeeCode(timeEntryDto.getEmployeeCode())
                .build();
    }

    private TimeEntryDto mapTimeEntryToDto(TimeEntry timeEntry) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        org.joda.time.LocalDate localDate = timeFormatter.parseLocalDate(timeEntry.getDay());
        TimeEntryDto timeEntryDto = new TimeEntryDto();
        timeEntryDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        timeEntryDto.setStart(timeEntry.getStart());
        timeEntryDto.setStop(timeEntry.getStop());
        timeEntryDto.setEmployeeCode(timeEntry.getEmployeeCode());
        return timeEntryDto;
    }

    private List<TimeEntryDto> mapTimeEntriesToDto(TimeEntries timeEntries) {
        return timeEntries.getTimeEntryList()
                .stream()
                .map(this::mapTimeEntryToDto)
                .toList();
    }

}
