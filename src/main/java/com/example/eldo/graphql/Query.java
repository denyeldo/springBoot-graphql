package com.example.eldo.graphql;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.eldo.jpa.Author;
import com.example.eldo.jpa.AuthorRepository;
import com.example.eldo.jpa.Book;
import com.example.eldo.jpa.BookRepository;

public class Query implements GraphQLQueryResolver {
	@Autowired
    private BookRepository bookRepository;
	@Autowired
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
}
