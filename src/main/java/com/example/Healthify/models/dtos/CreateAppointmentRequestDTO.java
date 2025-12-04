package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
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
public class CreateAppointmentRequestDTO {

    @NotBlank(message = "packageId is required")
    private String packageId;

    @NotNull(message = "dateTime is required")
    private LocalDateTime dateTime;
}
