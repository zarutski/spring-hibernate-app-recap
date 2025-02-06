package com.recap.self.springcourse.config.dao;

import com.recap.self.springcourse.config.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDAO {

    private static final String SELECT_SCRIPT = "SELECT p FROM Person p";

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true) // --- imported from Spring (transactions managed by Spring) [readonly_mark]
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(SELECT_SCRIPT, Person.class).getResultList();
    }

    public Person show(int id) {
        return null;
    }

    public void save(Person person) {
    }

    public void update(int id, Person person) {
    }

    public void delete(int id) {
    }

}
