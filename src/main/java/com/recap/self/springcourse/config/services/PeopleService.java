package com.recap.self.springcourse.config.services;

import com.recap.self.springcourse.config.models.Person;
import com.recap.self.springcourse.config.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// --- additional Transaction management despite Repository auto-transactions
//         [provide additional transactions control over business logic]
@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository repository;

    @Autowired
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person find(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional // --- overrides class-level annotation, more specific [readonly default is false]
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id); // --- set provided ID, so Spring will update existing entity [instead of saving new]
        repository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Person> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Person> findByNameOrderByAge(String name) {
        return repository.findByNameOrderByAge(name);
    }

    public List<Person> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<Person> findByNameStartingWith(String startingWith) {
        return repository.findByNameStartingWith(startingWith);
    }

    public List<Person> findByNameOrEmail(String name, String email) {
        return repository.findByNameOrEmail(name, email);
    }

}
