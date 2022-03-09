package com.loripin.auto.service;

import com.loripin.auto.model.Modification;
import com.loripin.auto.model.Spot;
import com.loripin.auto.model.User;
import com.loripin.auto.model.dto.SpotDto;
import com.loripin.auto.repos.SpotRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {

    private final
    SpotRepo spotRepo;

    public SpotService(SpotRepo spotRepo) {
        this.spotRepo = spotRepo;
    }

    public Spot save(Spot spot) {
        return spotRepo.save(spot);
    }


    public List<SpotDto> findAllByOrderByIdDesc(User user) {
        return spotRepo.findAllByOrderByIdDesc(user);
    }

    public Spot findById(Long id) {
        return spotRepo.getOne(id);
    }

    public List<SpotDto> findByModificationIdOrderByIdDesc(Modification modification, User user) {
        return spotRepo.findByModificationIdOrderByIdDesc(modification, user);
    }


    public List<Spot> findByModificationOrderByIdDesc(Modification modification) {
        return spotRepo.findByModificationOrderByIdDesc(modification);
    }

    public List<SpotDto> findByUserIdOrderByIdDesc(User user1, User user) {
        return spotRepo.findByUserIdOrderByIdDesc(user1, user);
    }
}
