package com.example.eldo.graphql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.eldo.jpa.Author;
import com.example.eldo.jpa.AuthorRepository;
import com.example.eldo.jpa.Book;
import com.example.eldo.jpa.BookRepository;

public class Mutation implements GraphQLMutationResolver {
	@Autowired
    private BookRepository bookRepository;
	@Autowired
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.get() == null) {
            System.out.println("The book to be updated was found"+ id);
        }
        //book.setPageCount(pageCount);

        bookRepository.save(book.get());

        return book.get();
    }
}
