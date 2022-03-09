package com.loripin.auto.service;

import com.loripin.auto.model.Manufacturer;
import com.loripin.auto.repos.ManufacturerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    private final
    ManufacturerRepo manufacturerRepo;

    public ManufacturerService(ManufacturerRepo manufacturerRepo) {
        this.manufacturerRepo = manufacturerRepo;
    }

    public List<Manufacturer> findAll() {
        return manufacturerRepo.findAll();
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepo.save(manufacturer);
    }

    public Manufacturer findById(Long id) {
        return manufacturerRepo.getOne(id);
    }

    public List<Manufacturer> findAllByOrderByIdAsc() {
        return manufacturerRepo.findAllByOrderByIdAsc();
    }

    public List<Manufacturer> findAllByOrderByNameAsc() {
        return manufacturerRepo.findAllByOrderByNameAsc();
    }
}
