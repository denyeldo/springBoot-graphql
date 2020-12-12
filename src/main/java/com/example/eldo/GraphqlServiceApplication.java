package com.example.eldo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.eldo.graphql.BookResolver;
import com.example.eldo.graphql.GraphQLErrorAdapter;
import com.example.eldo.graphql.Mutation;
import com.example.eldo.graphql.Query;
import com.example.eldo.jpa.Author;
import com.example.eldo.jpa.AuthorRepository;
import com.example.eldo.jpa.Book;
import com.example.eldo.jpa.BookRepository;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class GraphqlServiceApplication implements CommandLineRunner  {
	
	

	
	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;


	public static void main(String[] args) {
		SpringApplication.run(GraphqlServiceApplication.class, args);
	}
	
	@Override
    public void run(String... args) {

        System.out.println("StartApplication...");
        authorRepository.save(new Author("William","Shakesphere"));
        authorRepository.save(new Author("Italo","Calvino"));
        

        bookRepository.save(new Book("Book1", "true", 0, authorRepository.findAll().get(0)));
        bookRepository.save(new Book("Book2", "true", 0, authorRepository.findAll().get(1)));
        //bookRepository.save(new Book("Book3", "true", 0, new Author("William","Shakesphere")));

        System.out.println("\nfindAll()");
        bookRepository.findAll().forEach(x -> System.out.println(x));

        System.out.println("\nfindById(1L)");
        bookRepository.findById(1l).ifPresent(x -> System.out.println(x));

        System.out.println("\nfindByName('Node')");
        authorRepository.findByFirstName("William").forEach(x -> System.out.println(x));

    }
	
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(authorRepository, bookRepository);
	}

	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(authorRepository, bookRepository);
	}

	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			Author author = new Author("Herbert", "Schildt");
			authorRepository.save(author);

			bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
		};
	}

}
