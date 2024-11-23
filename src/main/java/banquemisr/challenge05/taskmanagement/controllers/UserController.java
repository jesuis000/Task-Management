package banquemisr.challenge05.taskmanagement.controllers;

import banquemisr.challenge05.taskmanagement.dto.RefreshTokenRequestDTO;
import banquemisr.challenge05.taskmanagement.dto.AuthDTO;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import banquemisr.challenge05.taskmanagement.models.UserRole;
import banquemisr.challenge05.taskmanagement.security.jwt.JwtUtil;
import banquemisr.challenge05.taskmanagement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserService userService;

    UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserModel user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassWord()));
        } catch (BadCredentialsException e) {
            System.out.println("--------->Incorrect UserName or password!!");
            throw new BadCredentialsException("Incorrect UserName or password!!", e);
        }

        UserModel userRecord = userService.getByUserName(user.getUserName());

        String role = userRecord.getUserRole().toString();

        String jwt = jwtUtil.generateToken(user.getUserName(), role);

        String refreshToken = jwtUtil.generateRefreshToken(user.getUserName());

        userRecord.setRefreshToken(refreshToken);

        userService.updateUser(userRecord);

        return ResponseEntity.ok(new AuthDTO(jwt, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserModel user) throws Exception {


        return ResponseEntity.ok(userService.insertUser(user));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {

        String refreshToken = refreshTokenRequestDTO.getRefreshToken();

        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String userName = jwtUtil.extractUserName(refreshToken);

            UserModel userModel = userService.getByUserName(userName);

            UserRole userRole = userModel.getUserRole();

            String userRefreshToken = userModel.getRefreshToken();

            if (refreshToken.equals(userRefreshToken)) {
                String jwt = jwtUtil.generateToken(userName, userRole.toString());

                return ResponseEntity.ok(new AuthDTO(jwt, refreshToken));
            }

        }
        throw new BadCredentialsException("Invalid Refresh Token");


    }
}

