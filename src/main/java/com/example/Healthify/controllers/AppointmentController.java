package com.example.Healthify.controllers;


import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.AppointmentResponseDTO;
import com.example.Healthify.models.dtos.AppointmentUpdateDTO;
import com.example.Healthify.models.dtos.CreateAppointmentRequestDTO;
import com.example.Healthify.models.dtos.UpdateAppointmentStatusRequest;
import com.example.Healthify.models.entities.Users;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.services.AppointmentServicesImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/appointment")
@Tag(name="Appointments APIs" ,description = "This endpoints will do all the medical appointments")
public class AppointmentController {

    @Autowired
    private AppointmentServicesImpl service;

    @Autowired
    private UserRepository userRepository;

    private Users getCurrentUser() {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }


    @PostMapping("/book")
    @Operation(summary = "Book an appointment")
    public AppointmentResponseDTO book(@Valid
                                           @RequestBody CreateAppointmentRequestDTO request) {

        Users user = getCurrentUser();
        return service.bookAppointment(request, String.valueOf(user.getUserId()));


    }


    @GetMapping
    @Operation(summary = "get all appointments added paginition")
    public Page<AppointmentResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Users user = getCurrentUser();
        return service.getAppointments(page, size, user.getUserId(), user.getRole().name());
    }



    @PutMapping("/update/{appointmentId}")
    @Operation(summary = "update appointments details by appointment Id")
    public AppointmentResponseDTO update(
            @PathVariable String appointmentId,
            @Valid @RequestBody AppointmentUpdateDTO request
    ) {
        Users user = getCurrentUser();
        return service.updateAppointment(
                appointmentId,
                request,
                user.getUserId(),
                user.getRole().name());
    }


    @PutMapping("/status/{appointmentId}")
    @Operation(summary = "update appointment status by appointmentId")
    public AppointmentResponseDTO updateStatus(
            @Valid
            @PathVariable String appointmentId,
            @RequestBody UpdateAppointmentStatusRequest request
    ) {
        return service.updateStatus(appointmentId, request);
    }



    @DeleteMapping("/delete/{appointmentId}")
    @Operation(summary = "delete by appointment Id")
    public void delete(@PathVariable String appointmentId) {
        service.deleteAppointment(appointmentId);
    }

}
