package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * @author Vincent Velthuizen
 * The concept of a book for which my library can have copies.
 */

@Entity
public class Book {

    @Id @GeneratedValue
    Long bookId;

    String title;

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
}
