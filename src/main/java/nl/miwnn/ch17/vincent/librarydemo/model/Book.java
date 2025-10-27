package nl.miwnn.ch17.vincent.librarydemo.model;

/**
 * @author Vincent Velthuizen
 * The concept of a book for which my library can have copies.
 */
public class Book {
    String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
