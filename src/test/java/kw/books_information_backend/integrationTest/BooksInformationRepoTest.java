package kw.books_information_backend.integrationTest;

import kw.books_information_backend.model.Book;
import kw.books_information_backend.model.Rating;
import kw.books_information_backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksInformationRepoTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3-alpine");

    @Autowired
    BookRepository bookRepository;

    @Test
    void connection() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
        List<Book> bookList = List.of(new Book(1L,"Tango","Sławomir Mrożek","drama",(short) 1964, Set.of(new Rating(1L,(byte)3))),
                new Book(2L,"Nineteen Eighty-Four","George Orwell","political fiction",(short) 1949,Set.of(new Rating(2L,(byte)5))));
        bookRepository.saveAll(bookList);
    }

    @Test
    void shouldReturnIfListOfBooksIsNotNull() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull();
    }


}
