package com.edm.edmapi.controller;

import com.edm.api.DispositionsApi;
import com.edm.edmapi.service.DispositionService;
import com.edm.model.dto.DispositionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DispositionController implements DispositionsApi {

    private final DispositionService dispositionService;

    @Override
    public ResponseEntity<Void> deleteDisposition(String employeeCode) {
        dispositionService.deleteDisposition(employeeCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<DispositionDto>> dispositionsGet() {
        List<DispositionDto> result = dispositionService.dispositionsGet();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<DispositionDto> dispositionsPost(DispositionDto dispositionDto) {
        DispositionDto result = dispositionService.dispositionsPost(dispositionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<List<DispositionDto>> getDispositionsByEmployeeCode(String employeeCode, LocalDate from, LocalDate to) {
        List<DispositionDto> result = dispositionService.getDispositionsByEmployeeCode(employeeCode, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<DispositionDto> updateDisposition(String employeeCode, DispositionDto dispositionDto) {
        DispositionDto result = dispositionService.updateDisposition(employeeCode, dispositionDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
