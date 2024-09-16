package kw.books_information_backend.dao;

public class SearchRequest {
    private String authorname;
    private String bookName;
    private String bookCategory;
    private Short yearOfPublication;
    private Byte rate;


    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
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
}
