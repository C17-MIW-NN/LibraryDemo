package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * The concept of a book for which my library can have copies.
 */

@Entity
public class Book {

    @Id @GeneratedValue
    Long bookId;

    @Column(unique = true)
    String title;

    @ManyToMany
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<Copy> copies;

    public int getNumberOfCopies() {
        return copies.size();
    }

    public int getAvailableNumberOfCopies() {
        int availableCopies = 0;

        for (Copy copy : copies) {
            if (copy.getAvailable()) {
                availableCopies++;
            }
        }

        return availableCopies;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }
}
