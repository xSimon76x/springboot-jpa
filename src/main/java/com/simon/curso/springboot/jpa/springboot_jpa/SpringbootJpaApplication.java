package com.simon.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.simon.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.simon.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		findOne();
	}	

	public void findOne() {
		// forma1
		// Person person = repository.findById(1L).orElseThrow();
		// forma2
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if(optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();
		// }
		// System.out.println(person);

		// forma3
		// si esta presente el objeto del id que le pasamos retorna person
		repository.findById(2L).ifPresent(person -> System.out.println(person));
		
		// forma4
		// para abreviar la forma pasada, es como que se intuye que el "person" 
		// del callback, se usara en el println asi que se le envia y este se imprime ahi
		repository.findById(2L).ifPresent(System.out::println);

	}
	
	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		// List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("JavaScript");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");
		
		persons.stream().forEach(person -> System.out.println(person));
	
		List<Object[]> personsValue = repository.obtenerPersonData();
		personsValue.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
			// System.out.println(person);
		});
	}

}
