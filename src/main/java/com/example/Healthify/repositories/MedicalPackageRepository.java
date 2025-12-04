package com.example.Healthify.repositories;

import com.example.Healthify.models.entities.MedicalPackage;
import com.example.Healthify.models.enums.PackageCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalPackageRepository extends JpaRepository<MedicalPackage,Long> {

    Optional<MedicalPackage> findByPackageId(String packageId);

    Page<MedicalPackage> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<MedicalPackage> findByCategory(PackageCategory category, Pageable pageable);

    Page<MedicalPackage> findByPriceBetween(Integer min, Integer max, Pageable pageable);

    void deleteByPackageId(String packageId);
}
