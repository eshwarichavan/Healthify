package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.AppointmentResponseDTO;
import com.example.Healthify.models.dtos.CreateAppointmentRequestDTO;
import com.example.Healthify.models.entities.Appointments;
import com.example.Healthify.models.entities.MedicalPackage;
import com.example.Healthify.models.entities.Users;
import com.example.Healthify.models.enums.AppointmentStatus;
import com.example.Healthify.repositories.AppointmentRepository;
import com.example.Healthify.repositories.MedicalPackageRepository;
import com.example.Healthify.repositories.UserRepository;
import com.example.Healthify.utils.Generators.AppointmentIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalPackageRepository medicalPackageRepository;



}
