package com.simon.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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
		personalizedQueries();
	}	

	// Cuando la operacion a la BD es solo un select (o que no modifiquen en la BD)
	// tambien se usa el transaccional, pero se especifica que el transaccional 
	// sera solo de lectura
	@Transactional(readOnly = true)
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
		
		repository.findByNameContaining("pe").ifPresent(System.out::println);
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

	// Cuando se realiza una operacion de delete, update, create a la BD
	// Se debe usar el Transaccional
	@Transactional
	public void create() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre:");
		String name = scanner.nextLine();
		System.out.println("Ingrese el apellido:");
		String lastname = scanner.nextLine();
		System.out.println("Ingrese el lenguaje de programacion:");
		String programmingLanguage = scanner.nextLine();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person);

		System.out.println(personNew);
		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional
	public void update() {
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona:");
		Long id = scanner.nextLong();
		
		
		Optional<Person> optionalPerson = repository.findById(id);
		
		if(optionalPerson.isPresent()){
			Person p = optionalPerson.orElseThrow();
			System.out.println(p);
			Scanner scanner2 = new Scanner(System.in);
			System.out.println("Ingrese el lenguaje de programacion:");
			String programmingLanguage = scanner2.nextLine();
			p.setProgrammingLanguage(programmingLanguage);
			Person personDB = repository.save(p);
			System.out.println(personDB);
			scanner2.close();
		} else {
			System.out.println("El usuario no existe");
		}
		scanner.close();
	}

	@Transactional
	public void delete() {
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id del usuario a eliminar");
		Long id = scanner.nextLong();
		repository.deleteById(id);//Tambien existe el detele() pero se pasa el objeto entity
		scanner.close();

		repository.findAll().forEach(System.out::println);
	}

	@Transactional
	public void delete2() {
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id del usuario a eliminar");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(
			repository::delete, //person -> repository.delete(person) 
			() -> System.out.println("Usuario no eliminado")
		);

		scanner.close();

		repository.findAll().forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){
		System.out.println("=========================Consulta solo por el nombre=========================");
		System.out.println("Ingrese el id del usuario");
		Scanner scanner = new Scanner(System.in);
		Long id = scanner.nextLong();
		scanner.close();
		String name = repository.getNameById(id);
		System.out.println("El nombre del usuario es: " + name);

		String fullname = repository.getFullNameById(id);
		System.out.println("El nombre completo del usuario es: " + fullname);

		List<Object []> person = repository.findAllMixPerson();
		person.forEach(p -> {
			System.out.println("ProgrammingLanguage="+p[1]+ " person=" +p[0]);
		});

		List<Person> personMix = repository.findAllPersonalizedObjectPerson();
		personMix.forEach(System.out::println);
	}

}
