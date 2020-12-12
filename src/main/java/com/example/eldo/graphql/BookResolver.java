package com.example.eldo.graphql;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.eldo.jpa.Author;
import com.example.eldo.jpa.AuthorRepository;
import com.example.eldo.jpa.Book;

public class BookResolver implements GraphQLResolver<Book> {
	@Autowired
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findById(book.getAuthor().getId()).get();
    }
}
