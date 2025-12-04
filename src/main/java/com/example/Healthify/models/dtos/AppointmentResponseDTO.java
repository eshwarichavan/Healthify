package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDTO {

    private String appointmentId;
    private String packageId;
    private String packageName;
    private Integer packagePrice;
    private String userId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String createdAt;
    private String updatedAt;
}
