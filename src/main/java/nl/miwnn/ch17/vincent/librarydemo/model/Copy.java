package nl.miwnn.ch17.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

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

    private LocalDate purchasedOn;

    @ManyToOne
    private Book book;

    public Copy(Book book) {
        this.book = book;
        this.available = DEFAULT_AVAILABLE;
        this.purchasedOn = LocalDate.now();
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

    public LocalDate getPurchasedOn() {
        return purchasedOn;
    }

    public void setPurchasedOn(LocalDate purchasedOn) {
        this.purchasedOn = purchasedOn;
    }
}
