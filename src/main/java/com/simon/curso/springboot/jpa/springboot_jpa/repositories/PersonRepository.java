package com.simon.curso.springboot.jpa.springboot_jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.simon.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
