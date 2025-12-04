package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.PackageCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMedicalPackageRequest {

    @NotBlank(message = "Package name is required")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500)
    private String description;

    @NotNull(message = "Category is required")
    private PackageCategory category;

    @NotNull(message = "Price is required")
    @Min(value = 100)
    private Integer price;

    private String imageURL;

}
