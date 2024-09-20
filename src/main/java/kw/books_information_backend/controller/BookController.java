package kw.books_information_backend.controller;

import jakarta.validation.Valid;
import kw.books_information_backend.NotFoundException;
import kw.books_information_backend.dao.SearchRequest;
import kw.books_information_backend.model.Book;
import kw.books_information_backend.model.Rating;
import kw.books_information_backend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.findBookById(id);

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
//            throw new NotFoundException("Book not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/books")
    public Book addOneBook(@Valid @RequestBody Book book) {
        return bookService.addOneBook(book);
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        try {
            bookService.updateBook(id, book);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/books/{id}/ratings")
    public ResponseEntity<Void> rateBook(@PathVariable Long id, @Valid @RequestBody Rating rating) {
        try {
            bookService.addRatingToBook(id, rating);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/books/search")
    public List<Book> searchRequest(@RequestParam(name = "authorName", required = false) String authorName,
                                    @RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "bookCategory", required = false) String bookCategory,
                                    @RequestParam(name = "yearOfPublication", required = false) Short yearOfPublication,
                                    @RequestParam(name = "rate", required = false) Byte rate
    ) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setAuthorName(authorName);
        searchRequest.setBookName(bookName);
        searchRequest.setBookCategory(bookCategory);
        searchRequest.setYearOfPublication(yearOfPublication);
        searchRequest.setRate(rate);

        return bookService.search(searchRequest);
    }

}
