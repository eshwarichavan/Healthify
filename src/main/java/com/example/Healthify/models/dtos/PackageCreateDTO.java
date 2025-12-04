package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.PackageCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageCreateDTO {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotNull(message = "category is required")
    private PackageCategory category;

    @NotNull
    @Positive(message = "price must be positive")
    private Integer price;

    private String imageURL;
}
