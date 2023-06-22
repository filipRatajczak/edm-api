package com.edm.edmapi.service;

import com.edm.model.dto.DispositionDto;
import com.google.protobuf.Empty;
import gft.edm.grpc.disposition.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DispositionService {

    private final DispositionServiceGrpc.DispositionServiceBlockingStub dispositionServiceBlockingStub;

    public void deleteDisposition(String employeeCode) {
        dispositionServiceBlockingStub.deleteDisposition(DeleteDispositionsByIdRequest.newBuilder().setId(employeeCode).build());
    }


    public List<DispositionDto> dispositionsGet() {
        Dispositions dispositions = dispositionServiceBlockingStub.getAllDisposition(Empty.getDefaultInstance());
        return mapDispositionsToDto(dispositions);
    }


    public DispositionDto dispositionsPost(DispositionDto dispositionDto) {
        Disposition disposition = dispositionServiceBlockingStub.createDisposition(CreateDispositionRequest.newBuilder().setDisposition(dispositionDtoToGrpcFormat(dispositionDto)).build());
        return mapDispositionToDto(disposition);
    }


    public List<DispositionDto> getDispositionsByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        Dispositions dispositions = dispositionServiceBlockingStub.getDispositionsByEmployeeCode(GetDispositionsByEmployeeCodeRequest.newBuilder().setEmployeeCode(employeeCode).setStart(from.toString()).setStop(to.toString()).build());
        return mapDispositionsToDto(dispositions);
    }


    public DispositionDto updateDisposition(String employeeCode, DispositionDto dispositionDto) {
        Disposition disposition = dispositionServiceBlockingStub.updateDisposition(UpdateDispositionRequest.newBuilder().setEmployeeCode(employeeCode).setDisposition(dispositionDtoToGrpcFormat(dispositionDto)).build());
        return mapDispositionToDto(disposition);
    }

    private Dispositions dispositionsDtoToGrpcFormat(List<DispositionDto> dispositionDtos) {
        List<Disposition> collect = dispositionDtos.stream()
                .map(this::dispositionDtoToGrpcFormat)
                .toList();
        return Dispositions.newBuilder().addAllDisposition(collect).build();
    }

    private Disposition dispositionDtoToGrpcFormat(DispositionDto dispositionDto) {
        return Disposition.newBuilder()
                .setDay(dispositionDto.getDay().toString())
                .setStart(dispositionDto.getStart())
                .setStop(dispositionDto.getStop())
                .setEmployeeCode(dispositionDto.getEmployeeCode())
                .build();
    }

    private DispositionDto mapDispositionToDto(Disposition disposition) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd");
        org.joda.time.LocalDate localDate = timeFormatter.parseLocalDate(disposition.getDay());
        DispositionDto dispositionDto = new DispositionDto();
        dispositionDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        dispositionDto.setStart(disposition.getStart());
        dispositionDto.setStart(disposition.getStop());
        dispositionDto.setEmployeeCode(disposition.getEmployeeCode());
        return dispositionDto;
    }

    private List<DispositionDto> mapDispositionsToDto(Dispositions dispositions) {
        return dispositions.getDispositionList()
                .stream()
                .map(this::mapDispositionToDto)
                .toList();
    }

}
