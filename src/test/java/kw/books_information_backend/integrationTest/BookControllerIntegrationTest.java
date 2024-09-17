package kw.books_information_backend.integrationTest;

import kw.books_information_backend.model.Book;
import kw.books_information_backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3-alpine");

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        List<Book> bookList = List.of(new Book(1L, "Tango", "Sławomir Mrożek", "drama", (short) 1964, (byte) 4),
                new Book(2L, "Nineteen Eighty-Four", "George Orwell", "political fiction", (short) 1949, (byte) 5));
        bookRepository.saveAll(bookList);
    }

    @Test
        // api/books
    void findAllBooks() {
        Book[] books = restTemplate.getForObject("/api/books", Book[].class);
        assertThat(books.length).isEqualTo(2);
    }

    @Test
    void findBookByValidID() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/1", HttpMethod.GET, null, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}