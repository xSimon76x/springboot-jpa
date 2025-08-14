package com.simon.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.simon.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.simon.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("select p from Person p where p.id not in ?1")
    List<Person> getPersonsDistinctIds(List<Long> ids);

    @Query("select p from Person p where p.id in ?1")
    List<Person> getPersonsByIds(List<Long> ids);

    @Query("select p.name, length(p.name) from Person p where length(p.name) = (select min(length(p.name)) from Person p)")
    List<Object []> getShorterName();
    
    @Query("select p from Person p where p.id = (select max(p.id) from Person p)")
    Optional<Person> getLastRegistration();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    Object getResumeAggregationFunction();

    @Query("select min(length(p.name)) from Person p")
    Integer getMinLengthName();
    
    @Query("select p.name, length(p.name) from Person p")
    List<Object []> getPersonNameLength();

    @Query("select count(p) from Person p")
    Long getTotalPerson();

    @Query("select min(p.id) from Person p")
    Long getMinId();

    @Query("select max(p.id) from Person p")
    Long getMaxId();

    List<Person> findByNameBetweenOrderByNameDescLastnameAsc(String name1, String name2);

    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name asc, p.lastname desc")
    List<Person> findAllBetweenIdOrder(Long c1, Long c2);
    // Para nombres que comiencen J, K, L....P
    // @Query("select p from Person p where p.name between 'J' and 'P' ")
    @Query("select p from Person p where p.id between 2 and 5")
    List<Person> findAllBetweenId();

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    // @Query("select p.name || ' ' || p.lastname as fullname from Person p where p.id=?1")
    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);
    
    //? Lo mismo que la funcion custom de arriba, pero viene del crud repository de JPA
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);
    
    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);
    
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    //? Dependiendo de si este metodo tiene 1 o 2 parametros definidos puede filtrar, aplicando correctamente la sobrecarga de parametros
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonData(String programmingLanguage);

    //?Esto no se podria hacer, como para aplicar sobrecarga de parametros, por que se llaman igual el metodo
    //? @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    //? List<Object[]> obtenerPersonData(String name);
 
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();
    
    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllPersonalizedObjectPerson();
    
    //? Para Person no es necesario definir todo el package y la ruta, por que es una clase entity
    //? Esta dentro del contexto de persistencia
    //? En cambio PersonDto no es una clase entity, esta fuera del contexto, por lo que se requiere hacer 
    //? esta definicion
    @Query("select new com.simon.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllDistinctNames();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllDistinctProgramingLanguage();
    
    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllDistinctProgramingLanguageCount();

    @Query("select lower(p.name) from Person p")
    List<String> findAllNameLower();

    @Query("select upper(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameUpper();
}
