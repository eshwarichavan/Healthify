package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.SignInRequestDTO;
import com.example.Healthify.models.dtos.SignInResponseDTO;
import com.example.Healthify.models.entities.Users;
import com.example.Healthify.repositories.RefreshTokenRepository;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    // Login :
    public SignInResponseDTO SignIn(SignInRequestDTO dto){

        // checking if data exists or not
        Users users=userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new CustomException("User not Found with the email id", HttpStatus.NOT_FOUND));

        // password match with existing password in db :
        if(!passwordEncoder.matches(dto.getPassword(), users.getPassword())){
            throw new CustomException("Invalid Password",HttpStatus.UNAUTHORIZED);
        }

        // Generating token using user's email & role :
        List<String> roles = Collections.singletonList(users.getRole().name());

        // for access token :
        String token= jwtUtil.generateToken(users.getEmail(), roles);
        System.out.println("Generated JWT Token : " + token);


        // for refresh token :
        String refreshToken = refreshTokenService.createRefreshToken(users.getId());

    return new SignInResponseDTO(
                users.getEmail(),
                users.getRole(),
                "Login Successfully",
                token,
                refreshToken
        );
    }


    // logout :
}
