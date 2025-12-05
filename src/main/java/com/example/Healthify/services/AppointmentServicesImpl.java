package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.AppointmentResponseDTO;
import com.example.Healthify.models.dtos.AppointmentUpdateDTO;
import com.example.Healthify.models.dtos.CreateAppointmentRequestDTO;
import com.example.Healthify.models.dtos.UpdateAppointmentStatusRequest;
import com.example.Healthify.models.entities.Appointments;
import com.example.Healthify.models.entities.MedicalPackage;
import com.example.Healthify.models.entities.Users;
import com.example.Healthify.models.enums.AppointmentStatus;
import com.example.Healthify.repositories.AppointmentRepository;
import com.example.Healthify.repositories.MedicalPackageRepository;
import com.example.Healthify.repositories.UserRepository;

import com.example.Healthify.utils.Generators.AppointmentIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentServicesImpl {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicalPackageRepository medicalPackageRepository;



    // book :
    public AppointmentResponseDTO bookAppointment(CreateAppointmentRequestDTO req,
                                                  String userId) {

        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException("User not found",HttpStatus.NOT_FOUND));

        if (appointmentRepository.existsByUser_UserIdAndDateTime(userId, req.getDateTime())) {
            throw new CustomException("You already have appointment booked at this time",HttpStatus.BAD_REQUEST);
        }

        MedicalPackage pkg = medicalPackageRepository.findByPackageId(req.getPackageId())
                .orElseThrow(() -> new CustomException("Package not found",HttpStatus.NOT_FOUND));

        Appointments a = Appointments.builder()
                .appointmentId(AppointmentIdGenerator.generate())
                .dateTime(req.getDateTime())
                .appointmentStatus(AppointmentStatus.PENDING)
                .user(user)
                .pkg(pkg)
                .createdAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(a);
        return map(a);
    }



    // update appointment status:
    public AppointmentResponseDTO updateStatus(String appointmentId,
                                               UpdateAppointmentStatusRequest req) {

        Appointments a = appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found",HttpStatus.NOT_FOUND));

        a.setAppointmentStatus(req.getStatus());
        a.setUpdatedAt(LocalDateTime.now());

        appointmentRepository.save(a);
        return map(a);
    }



    // update appointments :
    public AppointmentResponseDTO updateAppointment(String appointmentId,
                                                    AppointmentUpdateDTO req,
                                                    String userId,
                                                    String role) {

        Appointments a = appointmentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new CustomException("Appointment not found",HttpStatus.NOT_FOUND));

        if (!a.getUser().getId().equals(userId)) {
            throw new CustomException("You cannot edit someone else's appointment",HttpStatus.BAD_REQUEST);
        }

        a.setDateTime(req.getDateTime());
        a.setUpdatedAt(LocalDateTime.now());

        appointmentRepository.save(a);
        return map(a);
    }


    // get appointments :
    public Page<AppointmentResponseDTO> getAppointments(int page,
                                                        int size,
                                                        String userId,
                                                        String role) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Appointments> data;

        if (role.equals("ADMIN")) {
            data = appointmentRepository.findAll(pageable);
        } else {
            data = appointmentRepository.findByUser_UserId(userId, pageable);
        }

        return data.map(this::map);
    }



    // delete :
    public String deleteAppointment(String appointmentId) {
        appointmentRepository.deleteByAppointmentId(appointmentId);
        return "Appointment Deleted Successfully";
    }



    // mapper :
    private AppointmentResponseDTO map(Appointments a) {
        AppointmentResponseDTO res = new AppointmentResponseDTO();
        res.setAppointmentId(a.getAppointmentId());
        res.setDateTime(a.getDateTime());
        res.setAppointmentStatus(a.getAppointmentStatus());
        res.setUserId(a.getUser().getUserId());
        res.setPackageId(null);
        return res;
    }
}
