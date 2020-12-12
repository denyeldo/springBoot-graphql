package com.example.eldo.jpa;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	List<Author> findAll();
	
	List<Author> findByFirstName(String firstName);
	
}
