package com.example.Healthify.controllers;

import com.example.Healthify.models.dtos.SignInRequestDTO;
import com.example.Healthify.models.dtos.SignInResponseDTO;
import com.example.Healthify.models.entities.RefreshToken;
import com.example.Healthify.repositories.RefreshTokenRepository;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.services.AuthServiceImpl;
import com.example.Healthify.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Auth APIs" ,description = "This endpoints will do all the authentications")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Sign in :
    @PostMapping
    @Operation(summary = "Sign API")
    public ResponseEntity<SignInResponseDTO> signIn(
            @Valid
            @RequestBody SignInRequestDTO signInRequestDTO){

        return ResponseEntity.ok(authService.SignIn(signInRequestDTO));
    }


    //Refresh Token :
    @PostMapping("/refreshToken")
    @Operation(summary = "Generates refresh token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload){

        String rawToken = payload.get("refreshToken");

        if (rawToken == null || rawToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }

        // Find the matching hashed token manually
        RefreshToken matchingToken = refreshTokenRepository.findAll().stream()
                .filter(t -> passwordEncoder.matches(rawToken, t.getToken()))
                .findFirst()
                .orElse(null);

        if (matchingToken == null) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        // Check expiry
        if (matchingToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(matchingToken);
            return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
        }

        // Generate new JWT
        List<String> rolesList = List.of(matchingToken.getUser().getRole().name());
        String newJwt = jwtUtil.generateToken(matchingToken.getUser().getEmail(), rolesList);

        return ResponseEntity.ok(Map.of("token", newJwt));
    }

}
