package com.example.Healthify.configs;

import com.example.Healthify.models.entities.Users;
import com.example.Healthify.models.enums.Roles;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.utils.Generators.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    // this class is for seeded Admin :

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;


    @Override
    public void run(String... args) throws Exception {

        // Seeded Admin details :
        if(userRepository.findByEmail(adminEmail).isEmpty()){

            Users admin = Users.builder()
                    .userId(UserIdGenerator.generate())
                    .name(adminName)
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))  //Have to bcrypt this pass
                    .role(Roles.ADMIN)
                    .age(34)
                    .gender("Female")
                    .createdBy(null)
                    .updatedBy(null)
                    .build();

            userRepository.save(admin);
            System.out.println("Default ADMIN created : "+ adminEmail);
        }
        else{
            System.out.println("Admin Already Exists");
        }

    }
}
