package com.example.Healthify.models.dtos;

import com.example.Healthify.models.enums.PackageCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageResponseDTO {

    private String packageId;
    private String name;
    private String description;
    private PackageCategory category;
    private Integer price;
    private String imageURL;
    private String createdAt;
}
