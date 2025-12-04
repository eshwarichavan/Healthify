package com.example.Healthify.controllers;

import com.example.Healthify.models.dtos.PackageCreateDTO;
import com.example.Healthify.models.dtos.PackageResponseDTO;
import com.example.Healthify.models.dtos.UpdateMedicalPackageRequest;
import com.example.Healthify.services.MedicalPackageServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/packages")
public class MedicalPackageController {

    @Autowired
    private MedicalPackageServiceImpl medicalPackageService;

    @GetMapping
    public Page<PackageResponseDTO> getPackages(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return medicalPackageService.getPackages(name, category, minPrice, maxPrice, page, size);
    }

    @GetMapping("/{id}")
    public PackageResponseDTO getById(@PathVariable String packageId) {
        return medicalPackageService.getPackageById(packageId);
    }

    @PostMapping
    public PackageResponseDTO create(@Valid
                                         @RequestBody PackageCreateDTO req) {
        return medicalPackageService.createPackage(req);
    }


    @PutMapping("/{id}")
    public PackageResponseDTO update(@PathVariable String packageId,
                                         @Valid
                                         @RequestBody UpdateMedicalPackageRequest req){
        return medicalPackageService.updatePackage(packageId, req);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable String packageId) {
        medicalPackageService.deletePackage(packageId);
    }
}
