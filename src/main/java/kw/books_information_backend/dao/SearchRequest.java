package kw.books_information_backend.dao;

import java.util.Objects;

public class SearchRequest {
    private String authorName;
    private String bookName;
    private String bookCategory;
    private Short yearOfPublication;
    private Byte rate;


    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorname) {
        this.authorName = authorname;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Short getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Short yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchRequest that)) return false;
        return Objects.equals(authorName, that.authorName) && Objects.equals(bookName, that.bookName) && Objects.equals(bookCategory, that.bookCategory) && Objects.equals(yearOfPublication, that.yearOfPublication) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorName, bookName, bookCategory, yearOfPublication, rate);
    }
}
