package com.simon.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.simon.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);
    
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);
    
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    // Dependiendo de si este metodo tiene 1 o 2 parametros definidos puede filtrar, aplicando correctamente la sobrecarga de parametros
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonData(String programmingLanguage);

    // Esto no se podria hacer, como para aplicar sobrecarga de parametros, por que se llaman igual el metodo
    // @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    // List<Object[]> obtenerPersonData(String name);
 
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
}
