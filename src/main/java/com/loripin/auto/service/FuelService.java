package com.loripin.auto.service;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Fuel;
import com.loripin.auto.repos.FuelRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelService {

    public FuelRepo getFuelRepo() {
        return fuelRepo;
    }

    final
    FuelRepo fuelRepo;

    public FuelService(FuelRepo fuelRepo) {
        this.fuelRepo = fuelRepo;
    }

    public List<Fuel> findAllByOrderByIdAsc() {
        return fuelRepo.findAllByOrderByIdAsc();
    }

    public Fuel save(Fuel fuel) {
        return fuelRepo.save(fuel);
    }

    public Fuel findById(Integer id) {
        return fuelRepo.getOne(id);
    }
    public void deleteById(Integer id) {
        fuelRepo.deleteById(id);
    }
}
