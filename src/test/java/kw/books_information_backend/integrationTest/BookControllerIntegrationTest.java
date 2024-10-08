package kw.books_information_backend.integrationTest;

import kw.books_information_backend.model.Book;
import kw.books_information_backend.model.Rating;
import kw.books_information_backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    List<Book> savedBooks;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        List<Book> bookList = List.of(new Book(1L, "Tango", "Sławomir Mrożek", "drama", (short) 1964, Set.of(new Rating(1L, (byte) 3))),
                new Book(2L, "Nineteen Eighty Four", "George Orwell", "political fiction", (short) 1949, Set.of(new Rating(2L, (byte) 4))),
                new Book(3L, "The plague", "albertcamus", "noweel", (short) 17, Set.of(new Rating(3L, (byte) 1))));
        savedBooks = bookRepository.saveAll(bookList);
    }

    @Test
        // api/books
    void findAllBooks() {
        Book[] books = restTemplate.getForObject("/api/books", Book[].class);
        assertThat(books.length).isEqualTo(3);
    }

    @Test
    void findBookByValidID() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/" + savedBooks.get(0).getId(), HttpMethod.GET, null, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void findBookByNotFoundID() {
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/-1", HttpMethod.GET, null, Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addBook() {
        Book book = new Book(null, "Ferdydurke", "Witold Gombrowicz", "novel", (short) 1937, Set.of(new Rating(null, (byte) 5)));
        ResponseEntity<Book> response = restTemplate.exchange("/api/books", HttpMethod.POST, new HttpEntity<>(book), Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getBookName()).isEqualTo("Ferdydurke");
        assertThat(response.getBody().getAuthorName()).isEqualTo("Witold Gombrowicz");
        assertThat(response.getBody().getBookCategory()).isEqualTo("novel");
        assertThat(response.getBody().getYearOfPublication()).isEqualTo((short) 1937);
        assertThat(response.getBody().getRatings()).hasSize(1);
        var addRating = response.getBody().getRatings().stream().findFirst().get();
        assertThat(addRating.getRate()).isEqualTo((byte) 5);
    }

    @Test
    void updateBookIfExist() {
        assertThat(bookRepository.findById(savedBooks.get(2).getId())).isNotEmpty();

        Book updateBook = new Book(3L, "The Plague", "Albert Camus", "novel", (short) 1947, Set.of(new Rating(3L, (byte) 5)));
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/" + savedBooks.get(2).getId(), HttpMethod.PUT, new HttpEntity<>(updateBook), Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNull();

        Book book = bookRepository.findById(savedBooks.get(2).getId()).get();
        assertThat(book.getBookName()).isEqualTo(updateBook.getBookName());
        assertThat(book.getAuthorName()).isEqualTo(updateBook.getAuthorName());
        assertThat(book.getBookCategory()).isEqualTo(updateBook.getBookCategory());
        assertThat(book.getYearOfPublication()).isEqualTo(updateBook.getYearOfPublication());
        assertThat(book.getRatings()).hasSize(1);
        var updateRating = book.getRatings().stream().findFirst().get();
        assertThat(updateRating.getRate()).isEqualTo((byte) 5);
    }


    @Test
    void updateBookIfNotExist() {
        Book updateBook = new Book(3L, "The Plague", "Albert Camus", "novel", (short) 1947, Set.of(new Rating(3L, (byte) 5)));
        ResponseEntity<Book> response = restTemplate.exchange("/api/books/-1", HttpMethod.PUT, new HttpEntity<>(updateBook), Book.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addRatingIfBookExist() {
        assertThat(bookRepository.findById(savedBooks.get(2).getId())).isNotEmpty();
        Rating rating = new Rating(1L,(byte) 3);
        ResponseEntity<Void> response = restTemplate.exchange("/api/books/" + savedBooks.get(2).getId() + "/ratings", HttpMethod.POST, new HttpEntity<>(rating), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNull();

        Book bookWithAddedRating = bookRepository.findById(savedBooks.get(2).getId()).get();

        assertThat(bookWithAddedRating.getRatings()).hasSize(2);
        var addedRate = bookWithAddedRating.getRatings().stream().filter(n -> n.getRate() == 3).findFirst();
        assertThat(addedRate).isPresent();
    }

    @Test
    void addRatingIfBookNotExist() {
        Rating rating = new Rating(1L,(byte) 3);
        ResponseEntity<Void> response = restTemplate.exchange("/api/books/1562/ratings", HttpMethod.POST, new HttpEntity<>(rating), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteBookIfExist() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/books/" + +savedBooks.get(0).getId(), HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteBookIfNotExist() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/books/-1", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void searchBookByNameIfExist() {
        //"/api/books/search?authorName=Mrożek"
        ResponseEntity<Book[]> bookResponse = restTemplate.getForEntity("/api/books/search?authorName=Mrożek", Book[].class);

        List<Book> response = Arrays.asList(bookResponse.getBody());

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getId()).isNotNull();
        assertThat(response.get(0).getBookName()).isEqualTo("Tango");
        assertThat(response.get(0).getAuthorName()).isEqualTo("Sławomir Mrożek");
        assertThat(response.get(0).getBookCategory()).isEqualTo("drama");
        assertThat(response.get(0).getYearOfPublication()).isEqualTo((short) 1964);
        assertThat(response.get(0).getRatings()).isEqualTo(Set.of(new Rating(1L, (byte) 3)));

        //1L, "Tango", "Sławomir Mrożek", "drama", (short) 1964, Set.of(new Rating(1L, (byte) 3, new Book(),
    }

    @Test
    void searchBookByNameIfNotExist() {

        Book[] response = restTemplate.getForObject("/api/books/search?authorName=Mrozek", Book[].class);
        assertThat(response.length).isEqualTo(0);

    }
}