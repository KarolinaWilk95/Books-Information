package kw.books_information_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Min(1)
    @Max(5)
    private byte rate;


    public Rating() {
    }

    public Rating(Long id, byte rate) {
        this.id = id;
        this.rate = rate;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Min(1)
    @Max(5)
    public byte getRate() {
        return rate;
    }

    public void setRate(@Min(1) @Max(5) byte rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating rating)) return false;
        return rate == rating.rate && Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate);
    }
}
