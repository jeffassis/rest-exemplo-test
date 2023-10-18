package br.com.jeff.rest.exemplo.repository;

import br.com.jeff.rest.exemplo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
