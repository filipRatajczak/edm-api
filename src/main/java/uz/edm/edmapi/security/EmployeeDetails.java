package uz.edm.edmapi.security;

import com.edm.model.dto.EmployeeDto;
import com.edm.model.dto.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmployeeDetails implements UserDetails {

    private String username;
    private String password;
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static EmployeeDetails mapEmployeeDtoToEmployeeDetails(EmployeeDto employeeDto) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.role = employeeDto.getRole();
        employeeDetails.username = employeeDto.getEmail();
        employeeDetails.password = employeeDto.getPassword();
        return employeeDetails;
    }

}
