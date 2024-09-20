package kw.books_information_backend.controller;

import kw.books_information_backend.NotFoundException;
import kw.books_information_backend.dao.SearchRequest;
import kw.books_information_backend.model.Book;
import kw.books_information_backend.model.Rating;
import kw.books_information_backend.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks() {
        // given
        List<Book> savedBooks = new ArrayList<>();
        when(bookService.getAllBooks()).thenReturn(savedBooks);
        // when
        var result = bookController.getAllBooks();

        // then
        verify(bookService).getAllBooks();
        assertThat(result).isEqualTo(savedBooks);

    }

    @Test
    void findBookByIdIfExist() {
        // given
        var bookID = 1L;
        var existingBook = new Book();
        existingBook.setId(bookID);
        when(bookService.findBookById(bookID)).thenReturn(Optional.of(existingBook));
        // when
        var result = bookController.findBookById(bookID);
        // then
        verify(bookService).findBookById(bookID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(existingBook);
    }

    @Test
    void findBookByIdIfNotExist() {
        // given
        var bookID = 1L;
        var book = new Book();
        book.setId(bookID);

        when(bookService.findBookById(bookID)).thenReturn(Optional.empty());

        // when
        var result = bookController.findBookById(bookID);

        // then
        verify(bookService).findBookById(bookID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void addOneBook() {
        // given
        var book = new Book();
        book.setId(null);
        var newBook = new Book();
        book.setId(1L);
        when(bookService.addOneBook(book)).thenReturn(newBook);
        // when
        var result = bookController.addOneBook(book);
        // then
        verify(bookService).addOneBook(book);
        assertThat(result).isEqualTo(newBook);
    }

    @Test
    void updateBookIfExist() {
        // given
        var bookID = 1L;
        var existingBook = new Book();
        existingBook.setId(bookID);
        existingBook.setBookName("Tngo");
        existingBook.setAuthorName("Mrożek");
        existingBook.setBookCategory("drame");
        existingBook.setYearOfPublication((short) 124);
        existingBook.setRatings(Set.of(new Rating(1L, (byte) 3)));

        var updatedBook = new Book();
        updatedBook.setId(bookID);
        updatedBook.setBookName("Tango");
        updatedBook.setAuthorName("Sławomir Mrożek");
        updatedBook.setBookCategory("drama");
        updatedBook.setYearOfPublication((short) 1964);
        updatedBook.setRatings(Set.of(new Rating(1L, (byte) 3)));

        when(bookService.updateBook(bookID, updatedBook)).thenReturn(updatedBook);
        // when

        var result = bookController.updateBook(bookID, updatedBook);

        // then
        verify(bookService).updateBook(bookID, updatedBook);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(null);
    }

    @Test
    void updateBookIfNotExist() {
        // given
        var bookID = 1L;
        var updatedBook = new Book();
        updatedBook.setId(bookID);

        when(bookService.updateBook(bookID, updatedBook)).thenThrow(NotFoundException.class);

        // when
        var result = bookController.updateBook(bookID, updatedBook);

        // then
        verify(bookService).updateBook(bookID, updatedBook);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addRateIfBookExist() {
        //given
        var bookID = 1L;
        var updatedBook = new Book();
        Rating rating = new Rating(1L, (byte) 3);
        updatedBook.setId(bookID);

        when(bookService.addRatingToBook(bookID, rating)).thenReturn(updatedBook);
        //when

        var result = bookController.rateBook(bookID, rating);

        //then

        verify(bookService).addRatingToBook(bookID, rating);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(null);

    }

    @Test
    void addRateIfBookNotExist() {
        // given
        var bookID = 1L;
        var updatedBook = new Book();
        updatedBook.setId(bookID);
        Rating rating = new Rating(1L, (byte) 3);

        when(bookService.addRatingToBook(bookID, rating)).thenThrow(NotFoundException.class);
        // when
        var result = bookController.rateBook(bookID, rating);
        // then
        verify(bookService).addRatingToBook(bookID, rating);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteBookIfExist() {
        // given
        var bookID = 1L;

        doNothing().when(bookService).deleteBook(bookID);
        // when
        bookController.deleteBook(bookID);

        // then
        verify(bookService).deleteBook(bookID);
    }

    @Test
    void deleteBookIfNotExist() {
        // given
        var bookID = 1L;

        doThrow(NotFoundException.class).when(bookService).deleteBook(bookID);

        // when
        var result = bookController.deleteBook(bookID);

        // then
        verify(bookService).deleteBook(bookID);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void searchBook() {
        // given
        var request = new SearchRequest();
        request.setBookName("Tango");
        var book = new Book();
        book.setBookName("Tango");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookService.search(request)).thenReturn(bookList);
        // when
        var result = bookController.searchRequest(null, "Tango", null, null, null);
        // then

        verify(bookService).search(request);
        assertThat(result).isEqualTo(bookList);

    }


}