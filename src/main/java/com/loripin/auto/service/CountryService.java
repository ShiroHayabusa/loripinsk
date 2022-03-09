package com.loripin.auto.service;

import com.loripin.auto.model.Body;
import com.loripin.auto.model.Country;
import com.loripin.auto.repos.CountryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final
    CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public Country save(Country country) {
        return countryRepo.save(country);
    }

    public List<Country> findAll() {
        return countryRepo.findAll();
    }

    public List<Country> findAllByOrderByName() {
        return countryRepo.findAllByOrderByName();
    }

    public Country findById(Long id) {
        return countryRepo.getOne(id);
    }
    public void deleteById(Long id) {
        countryRepo.deleteById(id);
    }
}
