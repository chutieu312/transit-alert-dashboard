package com.transitdemo.auth;

import com.transitdemo.users.User;
import com.transitdemo.users.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        User user = userRepository.findByEmail(req.email()).orElseThrow();
        return ResponseEntity.ok(new TokenResponse(
                jwtService.generateToken(user),
                user.getFullName(),
                user.getRole().name()));
    }

    // --- DTOs ---
    record LoginRequest(
            @Email @NotBlank String email,
            @NotBlank String password) {}

    record TokenResponse(String token, String fullName, String role) {}
}
