package banquemisr.challenge05.taskmanagement.security.jwt;


import banquemisr.challenge05.taskmanagement.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userName = null;
        String token = null;

        try {
            String authorizationHeader = request.getHeader("Authorization");


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {

                token = authorizationHeader.substring(7);

                userName = jwtUtil.extractUserName(token);

            }
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            // update request object for customAuthenticationEntryPoint to checks and handle
            request.setAttribute("jwt_exception", e);
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtUtil.validateToken(token, userName)) {


                String role = jwtUtil.extractRole(token);


                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);


                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }

        filterChain.doFilter(request, response);

    }


    private String createErrorResponse(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now().toString());
            errorResponse.put("status", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", message);
            errorResponse.put("path", ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getRequestURI());

            return mapper.writeValueAsString(errorResponse);
        } catch (Exception e) {
            return "{\"error\": \"" + message + "\"}";
        }
    }
}