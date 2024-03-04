package uz.edm.edmapi.security;

import com.edm.model.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/api/v1/login").permitAll()
                                .requestMatchers("/api/v1/dispositions/**").hasAnyAuthority(
                                        Role.INTERNAL.name(),
                                        Role.EMPLOYEE.name(),
                                        Role.MANAGER.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/schedule/**").hasAnyAuthority(
                                        Role.EMPLOYEE.name(),
                                        Role.MANAGER.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/schedule/**").hasAnyAuthority(
                                        Role.MANAGER.name())
                                .requestMatchers("/api/v1/dispositionsRatio/**").hasAnyAuthority(
                                        Role.INTERNAL.name(),
                                        Role.EMPLOYEE.name(),
                                        Role.MANAGER.name())
                                .requestMatchers("/api/v1/employees/**").hasAnyAuthority(
                                        Role.INTERNAL.name(),
                                        Role.MANAGER.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/timeEntries/**").hasAnyAuthority(
                                        Role.INTERNAL.name(),
                                        Role.MANAGER.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/timeEntries/**").hasAnyAuthority(
                                        Role.INTERNAL.name(),
                                        Role.MANAGER.name(),
                                        Role.EMPLOYEE.name())
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
