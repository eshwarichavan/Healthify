package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.CreatePatientRequestDTO;
import com.example.Healthify.models.dtos.PatientResponseDTO;
import com.example.Healthify.models.entities.Users;
import com.example.Healthify.models.enums.Roles;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.utils.Generators.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //create user :
    public PatientResponseDTO createPatient(CreatePatientRequestDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("Email already exists", HttpStatus.CONFLICT);
        }

        Users user = Users.builder()
                .userId(UserIdGenerator.generate())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .age(dto.getAge())
                .gender(dto.getGender())
                .role(Roles.PATIENT)
                .build();

        userRepository.save(user);
        return mapToResponse(user);
    }



    // get all users :
    public Page<PatientResponseDTO> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.max(size, 1),
                Sort.by("createdAt").descending()
        );


        Page<Users> usersPage = userRepository.findAll(pageable);
        return usersPage.map(this::mapToResponse);
    }



    // get user by id :
    public PatientResponseDTO getPatient(String userId) {

        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("Patient not found", HttpStatus.NOT_FOUND));

        return mapToResponse(user);
    }



    // update patient details :
    public PatientResponseDTO updatePatient(String userId, CreatePatientRequestDTO dto) {

        // checking if patient exists in db
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("Patient not found", HttpStatus.NOT_FOUND));


        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }

        userRepository.save(user);
        return mapToResponse(user);
    }



    // delete patient :
    @Transactional
    public void deletePatient(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new CustomException("Patient not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteByUserId(userId);
    }


    // search by patient name or email :
    public Page<PatientResponseDTO> searchPatient(String query, int page,int size){
        Pageable pageable= PageRequest.of(Math.max(0,page), Math.max(1,size) , Sort.by("name").ascending());

        if(query == null || query.isBlank()){
            Page<Users> all=userRepository.findAll(pageable);
            return mapPageToDto(all);
        }

        String trimmed=query.trim();

        // to check if it's an email :
        if(trimmed.contains("@")){
            Optional<Users> exact=userRepository.findByEmail(trimmed);

            if(exact.isPresent()){
                PatientResponseDTO dto=mapToResponse(exact.get());
                return new PageImpl<>(List.of(dto),pageable,1);
            }
        }

        // searching by name contain :
        Page<Users> byName = userRepository.findByNameContainingIgnoreCase(trimmed, pageable);
        if (byName.hasContent()) {
            return mapPageToDto(byName);
        }

        // searching by email contains :
        Page<Users> byEmail = userRepository.findByEmailContainingIgnoreCase(trimmed, pageable);
        return mapPageToDto(byEmail);
    }



    // mapper :
    private PatientResponseDTO mapToResponse(Users user) {
        return PatientResponseDTO.builder()

                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }


    // mapper for page :
    private Page<PatientResponseDTO> mapPageToDto(Page<Users> page) {
        List<PatientResponseDTO> dtos = page.getContent().stream()
                .map(this::mapToResponse)
                .toList();
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

}
