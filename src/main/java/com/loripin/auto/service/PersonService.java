package com.loripin.auto.service;

import com.loripin.auto.model.Person;
import com.loripin.auto.model.Series;
import com.loripin.auto.repos.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final
    PersonRepo personRepo;

    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Person save(Person person) {
        return personRepo.save(person);
    }

    public List<Person> findAllByOrderByIdAsc() {
        return personRepo.findAllByOrderByIdAsc();
    }

    public Person findById(Long id) {
        return personRepo.getOne(id);
    }

    public List<Person> findBySeriesIdOrderByIdAsc(Long id) {
        return personRepo.findBySeriesIdOrderByIdAsc(id);
    }

    public List<Person> findBySeriesIdOrderByPoints2021Desc(Long id) {
        return personRepo.findBySeriesIdOrderByPoints2021Desc(id);
    }

    public List<Person> findAllByOrderByNameAsc() {
        return personRepo.findAllByOrderByNameAsc();
    }

    public List<Person> findByTeamIdOrderByNameAsc(Long id) {
        return personRepo.findByTeamIdOrderByNameAsc(id);
    }

    public List<Person> findByTeamIdAndNavigatorOrderByNameAsc(Long id, Boolean b) {
        return personRepo.findByTeamIdAndNavigatorOrderByNameAsc(id, b);
    }

    public List<Person> findBySeriesIdOrderByNameAsc(Long tmpSeries) {
        return personRepo.findBySeriesIdOrderByNameAsc(tmpSeries);
    }
}
