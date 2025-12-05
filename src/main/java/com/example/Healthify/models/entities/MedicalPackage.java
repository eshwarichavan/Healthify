package com.example.Healthify.models.entities;

import com.example.Healthify.models.enums.PackageCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data   // for lombok:
@AllArgsConstructor
@NoArgsConstructor
@Builder  // builder
@Table(name="medical_package")
public class MedicalPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "package_id")
    private String packageId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PackageCategory category;

    @Column(name = "price")
    private Integer price;

    @Column(name = "image_url")
    private String imageURL;

    // package to user (One package belongs to only one user)
//    @OneToOne(mappedBy = "packageId")
//    private Users user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
