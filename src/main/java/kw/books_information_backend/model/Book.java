package kw.books_information_backend.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Set<Rating> ratings = new HashSet<>();

    public Book() {
    }

    public Book(Long id, String bookName, String authorName, String bookCategory, short yearOfPublication, Set<Rating> ratings) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookCategory = bookCategory;
        this.yearOfPublication = yearOfPublication;
        this.ratings = ratings;
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

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", bookCategory='" + bookCategory + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", rate=" + ratings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return yearOfPublication == book.yearOfPublication && Objects.equals(id, book.id) && Objects.equals(bookName, book.bookName) && Objects.equals(authorName, book.authorName) && Objects.equals(bookCategory, book.bookCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, authorName, bookCategory, yearOfPublication);
    }
}
