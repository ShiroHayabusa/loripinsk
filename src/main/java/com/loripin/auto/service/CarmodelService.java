package com.loripin.auto.service;

import com.loripin.auto.model.Carmodel;
import com.loripin.auto.repos.CarmodelRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarmodelService {
    private final
    CarmodelRepo carmodelRepo;

    public CarmodelService(CarmodelRepo carmodelRepo) {
        this.carmodelRepo = carmodelRepo;
    }

    public List<Carmodel> findAll() {
        return carmodelRepo.findAll();
    }

    public Carmodel saveCarmodel(Carmodel carmodel) {
        return carmodelRepo.save(carmodel);
    }

    public Carmodel findById(Long id) {
        return carmodelRepo.getOne(id);
    }

    public List<Carmodel> findAllByOrderByIdAsc() {
        return carmodelRepo.findAllByOrderByIdAsc();
    }

    public List<Carmodel> findAllByOrderByManufacturerAsc() {
        return carmodelRepo.findAllByOrderByManufacturerAsc();
    }

    public List<Carmodel> findByManufacturerId(Long id) {
        return carmodelRepo.findByManufacturerId(id);
    }

    public List<Carmodel> findAllByOrderByNameAsc() {
        return carmodelRepo.findAllByOrderByNameAsc();
    }

    public List<Carmodel> findByManufacturerIdOrderByName(Long id) {
        return carmodelRepo.findByManufacturerIdOrderByName(id);
    }

    public void deleteById(Long id) {
        carmodelRepo.deleteById(id);
    }
}
