package kw.books_information_backend.service;

import kw.books_information_backend.NotFoundException;
import kw.books_information_backend.dao.BookDAO;
import kw.books_information_backend.dao.SearchRequest;
import kw.books_information_backend.model.Book;
import kw.books_information_backend.model.Rating;
import kw.books_information_backend.repository.BookRepository;
import kw.books_information_backend.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookDAO bookDAO;
    private final RatingRepository ratingRepository;


    public BookService(BookRepository bookRepository, BookDAO bookDAO, RatingRepository ratingRepository, RatingRepository ratingRepository1) {
        this.bookRepository = bookRepository;
        this.bookDAO = bookDAO;

        this.ratingRepository = ratingRepository1;
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

    public List<Book> search(SearchRequest request) {
        return bookDAO.findByCriteria(request);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setBookName(updatedBook.getBookName());
            book.setAuthorName(updatedBook.getAuthorName());
            book.setBookCategory(updatedBook.getBookCategory());
            book.setYearOfPublication(updatedBook.getYearOfPublication());
            book.setRatings(updatedBook.getRatings());
            return bookRepository.save(book);
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    public Book addRatingToBook (Long id, Rating rating) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.getRatings().add(rating);
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
