package com.example.eldo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eldo.jpa.BookRepository;

@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@RequestMapping(value = "/books")
	public ResponseEntity<Object> getBooks() {
		return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
	}

}
