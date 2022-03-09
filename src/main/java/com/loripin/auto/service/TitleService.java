package com.loripin.auto.service;

import com.loripin.auto.model.Title;
import com.loripin.auto.repos.TitleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {
    private final
    TitleRepo titleRepo;

    public TitleService(TitleRepo titleRepo) {
        this.titleRepo = titleRepo;
    }

    public List<Title> findAllByOrderByIdAsc() {
        return titleRepo.findAllByOrderByIdAsc();
    }

    public Title save(Title title) {
        return titleRepo.save(title);
    }
}
