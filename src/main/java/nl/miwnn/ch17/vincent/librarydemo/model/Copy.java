package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Vincent Velthuizen
 * A physical copy of a book that can be available or lend out.
 */
@Entity
public class Copy {
    private static final boolean DEFAULT_AVAILABLE = true;

    @Id @GeneratedValue
    private Long copyId;

    private Boolean available;

    @ManyToOne
    private Book book;

    public Copy(Book book) {
        this.book = book;
        this.available = DEFAULT_AVAILABLE;
    }

    /**
     * Should only be used by JPA
     */
    public Copy() {
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
