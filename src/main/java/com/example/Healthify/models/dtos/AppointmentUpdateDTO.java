package com.example.Healthify.models.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentUpdateDTO {

    @NotNull(message = "Appointment date/time is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime dateTime;

    private Long packageId;
}
