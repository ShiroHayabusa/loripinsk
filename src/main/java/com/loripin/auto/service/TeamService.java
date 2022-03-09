package com.loripin.auto.service;

import com.loripin.auto.model.Series;
import com.loripin.auto.model.Team;
import com.loripin.auto.repos.TeamRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final
    TeamRepo teamRepo;

    public TeamService(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    public List<Team> findAllByOrderByIdAsc() {
        return teamRepo.findAllByOrderByIdAsc();
    }

    public Team save(Team team) {
        return teamRepo.save(team);
    }

    public Team findById(Long id) {
        return teamRepo.getOne(id);
    }

    public List<Team> findBySeriesIdOrderByIdAsc(Long id) {
        return teamRepo.findBySeriesIdOrderByIdAsc(id);
    }

    public List<Team> findAllByOrderByNameAsc() {
        return teamRepo.findAllByOrderByNameAsc();
    }

    public List<Team> findBySeriesIdOrderByNameAsc(Long tmpSeries) {
        return teamRepo.findBySeriesIdOrderByNameAsc(tmpSeries);
    }

    public List<Team> findBySeriesIdOrderByPoints2021Desc(Long id) {
        return teamRepo.findBySeriesIdOrderByPoints2021Desc(id);
    }
}
