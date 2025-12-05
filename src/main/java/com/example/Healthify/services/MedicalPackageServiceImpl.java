package com.example.Healthify.services;

import com.example.Healthify.execptions.CustomException;
import com.example.Healthify.models.dtos.PackageCreateDTO;
import com.example.Healthify.models.dtos.PackageResponseDTO;
import com.example.Healthify.models.dtos.UpdateMedicalPackageRequest;
import com.example.Healthify.models.entities.MedicalPackage;
import com.example.Healthify.models.enums.PackageCategory;
import com.example.Healthify.repositories.MedicalPackageRepository;
import com.example.Healthify.utils.Generators.PackageIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicalPackageServiceImpl {

    @Autowired
    private MedicalPackageRepository medicalPackageRepository;

    // create package :
    public PackageResponseDTO createPackage(PackageCreateDTO r) {

        MedicalPackage p = MedicalPackage.builder()
                .name(r.getName())
                .description(r.getDescription())
                .category(r.getCategory())
                .price(r.getPrice())
                .imageURL(r.getImageURL())
                .packageId(PackageIdGenerator.generate())
                .createdAt(LocalDateTime.now())
                .build();

        medicalPackageRepository.save(p);
        return mapToResponse(p);
    }


    // update package :
    public PackageResponseDTO updatePackage(String packageId , UpdateMedicalPackageRequest r) {

        MedicalPackage p = medicalPackageRepository.findByPackageId(packageId)
                .orElseThrow(() -> new CustomException("Package not found", HttpStatus.NOT_FOUND));

        p.setName(r.getName());
        p.setDescription(r.getDescription());
        p.setCategory(r.getCategory());
        p.setPrice(r.getPrice());
        p.setImageURL(r.getImageURL());
        p.setUpdatedAt(LocalDateTime.now());

        medicalPackageRepository.save(p);
        return mapToResponse(p);
    }


    // delete the package using package id :
    public void deletePackage(String packageId) {
        medicalPackageRepository.deleteByPackageId(packageId);
    }


    // get by package id :
    public PackageResponseDTO getPackageById(String packageId) {
        MedicalPackage p = medicalPackageRepository.findByPackageId(packageId)
                .orElseThrow(() -> new CustomException("Package not found",HttpStatus.NOT_FOUND));
        return mapToResponse(p);
    }


    // get packages using name ,price & category :
    public Page<PackageResponseDTO> getPackages(String name, String category, Integer minPrice, Integer maxPrice, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MedicalPackage> data;

        if (name != null) {
            data = medicalPackageRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (category != null) {
            data = medicalPackageRepository.findByCategory(PackageCategory.valueOf(category), pageable);
        } else if (minPrice != null && maxPrice != null) {
            data = medicalPackageRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            data = medicalPackageRepository.findAll(pageable);
        }

        return data.map(this::mapToResponse);
    }


    //search package by name :
    public PackageResponseDTO getPackageByName(String name) {
        MedicalPackage p = medicalPackageRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CustomException("Package not found", HttpStatus.NOT_FOUND));
        return mapToResponse(p);
    }




    // mapper :
    private PackageResponseDTO mapToResponse(MedicalPackage p) {
        PackageResponseDTO res = new PackageResponseDTO();
        res.setPackageId(p.getPackageId());
        res.setName(p.getName());
        res.setDescription(p.getDescription());
        res.setCategory(p.getCategory());
        res.setPrice(p.getPrice());
        res.setImageURL(p.getImageURL());
        return res;
    }
}
