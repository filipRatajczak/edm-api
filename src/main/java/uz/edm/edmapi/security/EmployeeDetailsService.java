package uz.edm.edmapi.security;


import com.edm.model.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.edm.edmapi.service.EmployeeService;


@Component
@RequiredArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmployeeDto employeeDto = employeeService.getEmployeeByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee with email:  " + email + " not found."));
        return EmployeeDetails.mapEmployeeDtoToEmployeeDetails(employeeDto);
    }

}
