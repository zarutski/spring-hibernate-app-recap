package com.recap.self.springcourse.config.repositories;

import com.recap.self.springcourse.config.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// --- @Component subtype for JPA [additional exception handling (sql specific), auto-transaction management by Spring]
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
