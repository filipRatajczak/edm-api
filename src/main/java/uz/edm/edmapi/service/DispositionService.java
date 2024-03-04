package uz.edm.edmapi.service;


import lombok.RequiredArgsConstructor;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DispositionService {

    private final uz.edm.grpc.disposition.DispositionServiceGrpc.DispositionServiceBlockingStub dispositionServiceBlockingStub;

    public void deleteDisposition(String employeeCode) {
        dispositionServiceBlockingStub.deleteDisposition(uz.edm.grpc.disposition.DeleteDispositionsByIdRequest.newBuilder().setId(employeeCode).build());
    }


    public List<com.edm.model.dto.DispositionDto> dispositionsGet(LocalDate from, LocalDate to) {
        uz.edm.grpc.disposition.Dispositions dispositions = dispositionServiceBlockingStub.getAllDisposition(
                uz.edm.grpc.disposition.GetAllDispositionRequest
                        .newBuilder().
                        setFrom(from.toString())
                        .setTo(to.toString())
                        .build());
        return mapDispositionsToDto(dispositions);
    }


    public com.edm.model.dto.DispositionDto dispositionsPost(com.edm.model.dto.DispositionDto dispositionDto) {
        uz.edm.grpc.disposition.Disposition disposition = dispositionServiceBlockingStub.createDisposition(uz.edm.grpc.disposition.CreateDispositionRequest.newBuilder().setDisposition(dispositionDtoToGrpcFormat(dispositionDto)).build());
        return mapDispositionToDto(disposition);
    }


    public List<com.edm.model.dto.DispositionDto> getDispositionsByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        uz.edm.grpc.disposition.Dispositions dispositions = dispositionServiceBlockingStub.getDispositionsByEmployeeCode(uz.edm.grpc.disposition.GetDispositionsByEmployeeCodeRequest.newBuilder().setEmployeeCode(employeeCode).setStart(from.toString()).setStop(to.toString()).build());
        return mapDispositionsToDto(dispositions);
    }


    public com.edm.model.dto.DispositionDto updateDisposition(String employeeCode, com.edm.model.dto.DispositionDto dispositionDto) {
        uz.edm.grpc.disposition.Disposition disposition = dispositionServiceBlockingStub.updateDisposition(uz.edm.grpc.disposition.UpdateDispositionRequest.newBuilder().setEmployeeCode(employeeCode).setDisposition(dispositionDtoToGrpcFormat(dispositionDto)).build());
        return mapDispositionToDto(disposition);
    }

    public com.edm.model.dto.DispositionRatioDto dispositionRatioDtoGet(String employeeCode) {
        uz.edm.grpc.disposition.DispositionRatio dispositionRatio = dispositionServiceBlockingStub.getDispositionRatio(uz.edm.grpc.disposition.DispositionRatioRequest.newBuilder().setEmployeeCode(employeeCode).build());
        return mapDispositionRatioToDto(dispositionRatio);
    }

    private uz.edm.grpc.disposition.Dispositions dispositionsDtoToGrpcFormat(List<com.edm.model.dto.DispositionDto> dispositionDtos) {
        List<uz.edm.grpc.disposition.Disposition> collect = dispositionDtos.stream()
                .map(this::dispositionDtoToGrpcFormat)
                .toList();
        return uz.edm.grpc.disposition.Dispositions.newBuilder().addAllDisposition(collect).build();
    }

    private uz.edm.grpc.disposition.Disposition dispositionDtoToGrpcFormat(com.edm.model.dto.DispositionDto dispositionDto) {
        String id = "";

        if (Objects.nonNull(dispositionDto.getId())) {
            id = dispositionDto.getId();
        }

        return uz.edm.grpc.disposition.Disposition.newBuilder()
                .setId(id)
                .setDay(dispositionDto.getDay().toString())
                .setStart(dispositionDto.getStart())
                .setStop(dispositionDto.getStop())
                .setEmployeeCode(dispositionDto.getEmployeeCode())
                .build();
    }

    private com.edm.model.dto.DispositionDto mapDispositionToDto(uz.edm.grpc.disposition.Disposition disposition) {
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        org.joda.time.LocalDate localDate = timeFormatter.parseLocalDate(disposition.getDay());
        com.edm.model.dto.DispositionDto dispositionDto = new com.edm.model.dto.DispositionDto();
        dispositionDto.setId(disposition.getId());
        dispositionDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        dispositionDto.setStart(disposition.getStart());
        dispositionDto.stop(disposition.getStop());
        dispositionDto.setEmployeeCode(disposition.getEmployeeCode());
        return dispositionDto;
    }

    private List<com.edm.model.dto.DispositionDto> mapDispositionsToDto(uz.edm.grpc.disposition.Dispositions dispositions) {
        return dispositions.getDispositionList()
                .stream()
                .map(this::mapDispositionToDto)
                .toList();
    }

    private com.edm.model.dto.DispositionRatioDto mapDispositionRatioToDto(uz.edm.grpc.disposition.DispositionRatio dispositionRatio) {
        com.edm.model.dto.DispositionRatioDto dispositionRatioDto = new com.edm.model.dto.DispositionRatioDto();
        dispositionRatioDto.setRatio(dispositionRatio.getWorkingDispositionRatio());
        dispositionRatioDto.setEmployeeCode(dispositionRatio.getEmployeeCode());
        return dispositionRatioDto;
    }

}
