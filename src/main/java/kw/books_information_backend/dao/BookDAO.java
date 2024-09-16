package kw.books_information_backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kw.books_information_backend.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class BookDAO {

    private final EntityManager em;

    public BookDAO(EntityManager em) {
        this.em = em;
    }


    public List<Book> findByCriteria(SearchRequest request) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Book> root = cq.from(Book.class);
        if (request.getAuthorname() != null) {
            Predicate authorNamePredicate = cb.like(root.get("authorName"), "%" + request.getAuthorname() + "%");
            predicates.add(authorNamePredicate);
        }
        if (request.getBookName() != null) {
            Predicate bookNamePredicate = cb.like(root.get("bookName"), "%" + request.getBookName() + "%");
            predicates.add(bookNamePredicate);
        }
        if (request.getBookCategory() != null) {
            Predicate bookCategoryPredicate = cb.like(root.get("bookCategory"), "%" + request.getBookCategory() + "%");
            predicates.add(bookCategoryPredicate);
        }
        if (request.getYearOfPublication() != null) {
            Predicate bookYearOfPublicationPredicate = cb.like(root.get("yearOfPublication"), "%" + request.getYearOfPublication() + "%");
            predicates.add(bookYearOfPublicationPredicate);
        }
        if (request.getRate() != null) {
            Predicate bookRatePredicate = cb.like(root.get("rate"), "%" + request.getRate() + "%");
            predicates.add(bookRatePredicate);
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<Book> query = em.createQuery(cq);
        return query.getResultList();
    }
}
