package com.example.Healthify.repositories;

import com.example.Healthify.models.entities.Appointments;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {

    Page<Appointments> findByUser_UserId(String userId, Pageable pageable);

    boolean existsByUser_UserIdAndDateTime(String userId, LocalDateTime dateTime);

    Optional<Appointments> findByAppointmentId(String appointmentId);

    void deleteByAppointmentId(String appointmentId);
}
