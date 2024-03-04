package uz.edm.edmapi.controller;

import com.edm.api.EmployeesApi;
import com.edm.model.dto.EmployeeDto;
import com.edm.model.dto.EmployeeViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.edm.edmapi.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeesApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<Void> deleteByEmployeeCode(String employeeCode) {
        employeeService.deleteByEmployeeCode(employeeCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<EmployeeViewDto>> employeesGet() {
        List<EmployeeViewDto> result = employeeService.employeesGet();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<EmployeeViewDto> employeesPost(EmployeeDto employeeDto) {
        EmployeeViewDto result = employeeService.employeesPost(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<EmployeeViewDto> getByEmployeeCode(String employeeCode) {
        EmployeeViewDto result = employeeService.getByEmployeeCode(employeeCode);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<EmployeeViewDto> updateByEmployeeCode(String employeeCode, EmployeeDto employeeDto) {
        EmployeeViewDto result = employeeService.updateByEmployeeCode(employeeCode, employeeDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
