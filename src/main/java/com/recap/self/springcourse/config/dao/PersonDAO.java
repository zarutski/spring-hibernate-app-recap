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

    private static final String SELECT_SCRIPT = "FROM Person p";

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

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person persisted = session.find(Person.class, id);
        if (persisted != null) {
            persisted.setName(person.getName());
            persisted.setSurname(person.getSurname());
            persisted.setAge(person.getAge());
            persisted.setEmail(person.getEmail());
        }
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.find(Person.class, id);
        if (person != null) {
            session.remove(person);
        }
    }
}
