package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * The genre of a book.
 */

@Entity
public class Genre {

    @Id @GeneratedValue
    private Long genreId;

    @Column(unique = true)
    private String shortName;

    @Column(unique = true)
    private String fullName;

    @OneToOne
    private Book exampleBook;

    @OneToMany(mappedBy = "genre")
    private Set<Book> books = new HashSet<>();

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        if (fullName == null) {
            return shortName;
        }
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Book getExampleBook() {
        if (exampleBook == null) {
            return books.iterator().next();
        }
        return exampleBook;
    }

    public void setExampleBook(Book exampleBook) {
        this.exampleBook = exampleBook;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
