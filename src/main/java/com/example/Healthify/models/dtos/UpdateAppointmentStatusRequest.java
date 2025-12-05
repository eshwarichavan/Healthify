package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAppointmentStatusRequest {

    @NotNull(message = "Status is required")
    private AppointmentStatus status;
}
