package com.example.Healthify.services;

import com.example.Healthify.models.dtos.CreatePatientRequestDTO;
import com.example.Healthify.models.dtos.PatientResponseDTO;
import java.util.List;

public interface UserService {

    PatientResponseDTO createUser(CreatePatientRequestDTO dto);
    PatientResponseDTO getUser(String userId);
    List<PatientResponseDTO> getAllUsers();
    PatientResponseDTO updateUser(String userId, CreatePatientRequestDTO dto);
    void deleteUser(String userId);
}
