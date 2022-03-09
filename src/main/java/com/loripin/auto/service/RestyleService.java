package com.loripin.auto.service;

import com.loripin.auto.model.Generation;
import com.loripin.auto.model.Restyle;
import com.loripin.auto.repos.RestyleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestyleService {
    private final
    RestyleRepo restyleRepo;

    public RestyleService(RestyleRepo restyleRepo) {
        this.restyleRepo = restyleRepo;
    }

    public Restyle saveRestyle(Restyle restyle) {
        return restyleRepo.save(restyle);
    }

    public Restyle findById(Long id) {
        return restyleRepo.getOne(id);
    }

    public List<Restyle> findAll() {
        return restyleRepo.findAll();
    }

    public List<Restyle> findAllByOrderByIdAsc() {
        return restyleRepo.findAllByOrderByIdAsc();
    }

    public List<Restyle> findAllByOrderByNameAsc() {
        return restyleRepo.findAllByOrderByNameAsc();
    }

    public List<Restyle> findByGenerationIdOrderByNameAsc(Long id) {
        return restyleRepo.findByGenerationIdOrderByNameAsc(id);
    }
}
