package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.entities.RefreshToken;
import com.example.Healthify.repositories.RefreshTokenRepository;
import com.example.Healthify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    //create refresh token :
    public String createRefreshToken(Long userId){

        String rawToken = UUID.randomUUID().toString();

        // hashed refresh token :
        String hashedToken = passwordEncoder.encode(rawToken);


        RefreshToken token=new RefreshToken();
        token.setUser(userRepository.findById(userId).get());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(hashedToken);

        refreshTokenRepository.save(token);  //storing hashed token in db

        return rawToken;  // returning raw token to the frontend
    }



    //validating refresh token :
    public  RefreshToken validateRefreshToken(String rawToken,Long userId){

        RefreshToken stored = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("Refresh token not found", HttpStatus.BAD_REQUEST));

        if (stored.getExpiryDate().isBefore(Instant.now())) {
            throw new CustomException("Refresh token expired", HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(rawToken, stored.getToken())) {
            throw new CustomException("Invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        return stored;
    }


    //to check if the token expired :
    public boolean isTokenExpired(RefreshToken token){
        return token.getExpiryDate().isBefore(Instant.now());
    }
}
