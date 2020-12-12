package com.example.eldo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eldo.jpa.AuthorRepository;

@RestController
public class AuthorController {
	
	@Autowired
	AuthorRepository authorRepository;

	@RequestMapping(value = "/authors")
	public ResponseEntity<Object> getAuthors() {
		return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
	}

}
