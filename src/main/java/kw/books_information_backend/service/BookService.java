package kw.books_information_backend.service;

import kw.books_information_backend.NotFoundException;
import kw.books_information_backend.model.Book;
import kw.books_information_backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addOneBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }


    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setBookName(updatedBook.getBookName());
            book.setAuthorName(updatedBook.getAuthorName());
            book.setBookCategory(updatedBook.getBookCategory());
            book.setYearOfPublication(updatedBook.getYearOfPublication());
            book.setRate(updatedBook.getRate());
            return bookRepository.save(book);
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    public void deleteBook(Long id) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new NotFoundException("Book not found");
        }

    }
}
