package com.loripin.auto.service;

import com.loripin.auto.model.Qualification;
import com.loripin.auto.repos.QualificationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificationService {
    private final
    QualificationRepo qualificationRepo;

    public QualificationService(QualificationRepo qualificationRepo) {
        this.qualificationRepo = qualificationRepo;
    }

    public Qualification save(Qualification qualification) {
        return qualificationRepo.save(qualification);
    }

    public List<Qualification> findByRaceIdOrderByPosition(Long id) {
        return qualificationRepo.findByRaceIdOrderByPosition(id);
    }

    public Qualification findById(Long id) {
        return qualificationRepo.getOne(id);
    }
}
