package com.example.Healthify.models.entities;

import com.example.Healthify.models.entities.Appointments;
import com.example.Healthify.models.entities.MedicalPackage;
import com.example.Healthify.models.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    // user to package (one user has only one package)
    @OneToOne
    @JoinColumn(name = "package_id")
    private MedicalPackage medicalPackage;

    // user to appointment (One User can have mul. appointments)
    @OneToMany(mappedBy = "user")
    private List<Appointments> appointments;
}
