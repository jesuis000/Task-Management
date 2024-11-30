package banquemisr.challenge05.taskmanagement.security;

import banquemisr.challenge05.taskmanagement.exception.ErrorResponse;
import banquemisr.challenge05.taskmanagement.security.jwt.JwtFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;

    SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter) {

        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationEntryPoint customauthenticationEntryPoint() {
        return (request, response, authException) -> {
            String errorMessage = "Authentication Failed";

            Throwable jwtException = (Throwable) request.getAttribute("jwt_exception");

            if (jwtException instanceof MalformedJwtException) {
                errorMessage = "Malformed JWT Token";
            } else if (jwtException instanceof SignatureException) {
                errorMessage = "Invalid JWT Signature";

            } else if (jwtException instanceof ExpiredJwtException) {
                errorMessage = "Expired JWT";
            }

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            ErrorResponse errorResponse = new ErrorResponse(
                    errorMessage,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    new HashMap<>()
            );

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customauthenticationEntryPoint())
                )
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/users/login/**").permitAll()
                                .requestMatchers("/users/register/**").permitAll()
                                .requestMatchers("/users/refresh/**").permitAll()
                                .requestMatchers("/tasks/**").authenticated()

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}