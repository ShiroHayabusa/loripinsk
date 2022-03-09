package com.loripin.auto.service;

import com.loripin.auto.model.Body;
import com.loripin.auto.repos.BodyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyService {
    private final
    BodyRepo bodyRepo;

    public BodyService(BodyRepo bodyRepo) {
        this.bodyRepo = bodyRepo;
    }

    public List<Body> findAllByOrderByIdAsc() {
        return bodyRepo.findAllByOrderByIdAsc();
    }

    public Body save(Body body) {
        return bodyRepo.save(body);
    }

    public List<Body> findAllByOrderByNameAsc() {
        return bodyRepo.findAllByOrderByNameAsc();
    }

    public Body findById(Long id) {
        return bodyRepo.getOne(id);
    }

    public void deleteById(Long id) {
        bodyRepo.deleteById(id);
    }
}
