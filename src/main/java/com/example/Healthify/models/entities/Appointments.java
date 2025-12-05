package com.example.Healthify.models.entities;

import com.example.Healthify.models.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="appointment_id")
    private String appointmentId;

    @Column(name="date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status")
    private AppointmentStatus appointmentStatus;

    // Many appointments can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // These cannot be Users directly, convert to Long (FK)
    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;



}
