package kw.books_information_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book { //fields
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String bookName;

    @Column
    private String authorName;

    @Column
    private String bookCategory;

    @Column
    private short yearOfPublication;

    @Column
    @Min(1)
    @Max(5)
    private byte rate;


    public Book() {

    }

    public Book(Long id, String bookName, String authorName, String bookCategory, short yearOfPublication, byte rate) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookCategory = bookCategory;
        this.yearOfPublication = yearOfPublication;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public short getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(short yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public byte getRate() {
        return rate;
    }

    public void setRate(byte rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return yearOfPublication == book.yearOfPublication && rate == book.rate && Objects.equals(id, book.id) && Objects.equals(bookName, book.bookName) && Objects.equals(authorName, book.authorName) && Objects.equals(bookCategory, book.bookCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, authorName, bookCategory, yearOfPublication, rate);
    }
}
