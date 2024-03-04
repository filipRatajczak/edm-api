package uz.edm.edmapi.controller;

import com.edm.api.DispositionsApi;
import com.edm.model.dto.DispositionDto;
import com.edm.model.dto.DispositionRatioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.edm.edmapi.service.DispositionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DispositionController implements DispositionsApi {

    private final DispositionService dispositionService;


    @Override
    public ResponseEntity<DispositionRatioDto> getDispositionRatioByEmployeeCode(String employeeCode) {
        log.info("getDispositionRatioByEmployeeCode INVOKED with employeecode:[{}]", employeeCode);
        DispositionRatioDto result = dispositionService.dispositionRatioDtoGet(employeeCode);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @Override
    public ResponseEntity<Void> deleteDisposition(String employeeCode) {
        log.info("deleteDisposition() method invoked with employeeCode:[{}]", employeeCode);
        dispositionService.deleteDisposition(employeeCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Override
    public ResponseEntity<List<DispositionDto>> dispositionsGet(LocalDate from, LocalDate to) {
        log.info("dispositionsGet() method invoked with params: [from:{}, to{}]", from, to);
        List<DispositionDto> result = dispositionService.dispositionsGet(from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<DispositionDto> dispositionsPost(DispositionDto dispositionDto) {
        log.info("dispositionsPost() method invoked with dispositionDto:[{}]", dispositionDto.toString());
        DispositionDto result = dispositionService.dispositionsPost(dispositionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    @Override
    public ResponseEntity<List<DispositionDto>> getDispositionsByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        log.info("getDispositionsByEmployeeCode() method invoked with employeeCode:[{}], from:[{}], to:[{}]", employeeCode, from, to);
        List<DispositionDto> result = dispositionService.getDispositionsByEmployeeCode(employeeCode, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @Override
    public ResponseEntity<DispositionDto> updateDisposition(String employeeCode, DispositionDto dispositionDto) {
        log.info("updateDisposition() method invoked with employeeCode:[{}], dispositionDto:[{}]", employeeCode, dispositionDto);
        DispositionDto result = dispositionService.updateDisposition(employeeCode, dispositionDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
