package uz.edm.edmapi.service;

import com.edm.model.dto.DispositionDto;
import com.edm.model.dto.DispositionRatioDto;
import uz.edm.grpc.disposition.CreateDispositionRequest;
import uz.edm.grpc.disposition.DeleteDispositionsByIdRequest;
import uz.edm.grpc.disposition.Disposition;
import uz.edm.grpc.disposition.DispositionRatio;
import uz.edm.grpc.disposition.DispositionRatioRequest;
import uz.edm.grpc.disposition.DispositionServiceGrpc;
import uz.edm.grpc.disposition.Dispositions;
import uz.edm.grpc.disposition.GetAllDispositionRequest;
import uz.edm.grpc.disposition.GetDispositionsByEmployeeCodeRequest;
import uz.edm.grpc.disposition.UpdateDispositionRequest;
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


    public List<DispositionDto> dispositionsGet(LocalDate from, LocalDate to) {
        Dispositions dispositions = dispositionServiceBlockingStub.getAllDisposition(
                GetAllDispositionRequest
                        .newBuilder().
                        setFrom(from.toString())
                        .setTo(to.toString())
                        .build());
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

    public DispositionRatioDto dispositionRatioDtoGet(String employeeCode) {
        DispositionRatio dispositionRatio = dispositionServiceBlockingStub.getDispositionRatio(DispositionRatioRequest.newBuilder().setEmployeeCode(employeeCode).build());
        return mapDispositionRatioToDto(dispositionRatio);
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
        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        org.joda.time.LocalDate localDate = timeFormatter.parseLocalDate(disposition.getDay());
        DispositionDto dispositionDto = new DispositionDto();
        dispositionDto.setDay(java.time.LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth()));
        dispositionDto.setStart(disposition.getStart());
        dispositionDto.stop(disposition.getStop());
        dispositionDto.setEmployeeCode(disposition.getEmployeeCode());
        return dispositionDto;
    }

    private List<DispositionDto> mapDispositionsToDto(Dispositions dispositions) {
        return dispositions.getDispositionList()
                .stream()
                .map(this::mapDispositionToDto)
                .toList();
    }

    private DispositionRatioDto mapDispositionRatioToDto(DispositionRatio dispositionRatio) {
        DispositionRatioDto dispositionRatioDto = new DispositionRatioDto();
        dispositionRatioDto.setRatio(dispositionRatio.getWorkingDispositionRatio());
        dispositionRatioDto.setEmployeeCode(dispositionRatio.getEmployeeCode());
        return dispositionRatioDto;
    }

}
