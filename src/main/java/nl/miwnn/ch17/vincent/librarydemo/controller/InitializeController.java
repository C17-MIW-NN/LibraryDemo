package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.model.Copy;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

/**
 * @author Vincent Velthuizen
 * Initialises the database with example data
 */
@Controller
public class InitializeController {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public InitializeController(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (bookRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        makeBook("The Hobbit", 3);
        makeBook("The Lord of the Rings", 5);
        makeBook("The Name of the Wind", 2);
        makeBook("A Wise Man's Fear", 1);
    }

    private Book makeBook(String title, int numberOfCopies) {
        Book book = new Book();

        book.setTitle(title);
        bookRepository.save(book);

        for (int copyNr = 0; copyNr < numberOfCopies; copyNr++) {
            makeCopy(book, copyNr % 2 == 0);
        }

        return book;
    }

    private Copy makeCopy(Book book, boolean available) {
        Copy copy = new Copy(book);

        copy.setAvailable(available);
        copyRepository.save(copy);

        return copy;
    }
}
