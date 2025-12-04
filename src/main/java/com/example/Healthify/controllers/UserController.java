package com.example.Healthify.controllers;


import com.example.Healthify.models.dtos.CreatePatientRequestDTO;
import com.example.Healthify.models.dtos.PatientResponseDTO;
import com.example.Healthify.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name="Users API",description = "This endpoints will do all the users operations")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @PostMapping("/create")
    @Operation(summary = "create Patient only for ADMIN ")
    public PatientResponseDTO createPatient(
            @Valid
            @RequestBody CreatePatientRequestDTO dto) {

        return userService.createPatient(dto);
    }



    @GetMapping("/all")
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAllPatients(page, size));
    }



    @PutMapping("/update/{userId}")
    @Operation(summary = "update patient details by its patientId ")
    public PatientResponseDTO updatePatient(
            @Valid
            @PathVariable String userId,
            @RequestBody CreatePatientRequestDTO dto) {

        return userService.updatePatient(userId, dto);
    }


    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "delete patient details by patientId")
    public String deletePatient(
            @PathVariable String userId) {

        userService.deletePatient(userId);
        return "Patient deleted successfully";
    }


    // search by name or email :
    @GetMapping("/search")
    @Operation(summary = "Search patient details by name & email")
    public ResponseEntity<Page<PatientResponseDTO>> searchPatient(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<PatientResponseDTO> result=userService.searchPatient(query,page,size);
        return ResponseEntity.ok(result);
    }


}
