package uz.edm.edmapi.controller;

import com.edm.api.DispositionsApi;
import uz.edm.edmapi.service.DispositionService;
import com.edm.model.dto.DispositionDto;
import com.edm.model.dto.DispositionRatioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("deleteDisposition() METHOD INVOKED WITH employeeCode:[{}]", employeeCode);
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
        log.info("dispositionsPost() METHOD INVOKED with dispositionDto:[{}]", dispositionDto.toString());
        DispositionDto result = dispositionService.dispositionsPost(dispositionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<List<DispositionDto>> getDispositionsByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        log.info("getDispositionsByEmployeeCode() METHOD INVOKED with employeeCode:[{}], from:[{}], to:[{}]", employeeCode, from, to);
        List<DispositionDto> result = dispositionService.getDispositionsByEmployeeCode(employeeCode, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<DispositionDto> updateDisposition(String employeeCode, DispositionDto dispositionDto) {
        log.info("updateDisposition() METHOD INVOKED with employeeCode:[{}], dispositionDto:[{}]", employeeCode, dispositionDto);
        DispositionDto result = dispositionService.updateDisposition(employeeCode, dispositionDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
