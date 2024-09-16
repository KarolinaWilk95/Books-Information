package kw.books_information_backend.controller;

import jakarta.validation.Valid;
import kw.books_information_backend.NotFoundException;
import kw.books_information_backend.model.Book;
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
            throw new NotFoundException("Book not found");
        }
    }

    @PostMapping("/api/books")
    public Book addOneBook(@Valid @RequestBody Book book) {
        Book newBook = bookService.addOneBook(book);
        return this.bookService.addOneBook(newBook);
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<Void> updateBook(@Valid @PathVariable Long id, @RequestBody Book book) {
        try {
            bookService.updateBook(id, book);
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

}
