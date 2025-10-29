package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Author;
import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.model.Copy;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Initialises the database with example data
 */
@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public InitializeController(AuthorRepository authorRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
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
        Author sanderson = makeAuthor("Brandon Sanderson");
        Author rothfuss = makeAuthor("Patrick Rothfuss");
        Author tolkien = makeAuthor("J.R.R. Tolkien");

        makeBook("The Hobbit", 3, tolkien);
        makeBook("The Lord of the Rings", 5, tolkien);
        makeBook("The Name of the Wind", 2, rothfuss);
        makeBook("A Wise Man's Fear", 1, rothfuss);
        makeBook("Mistborn: The Final Empire", 3, sanderson);
        makeBook("Mistborn: The Well of Ascension", 2, sanderson);
        makeBook("Mistborn: The Hero of Ages", 2, sanderson);
        makeBook("Fantasy: a collection", 2, tolkien, rothfuss, sanderson);
    }

    private Author makeAuthor(String name) {
        Author author = new Author();

        author.setName(name);

        authorRepository.save(author);

        return author;
    }

    private Book makeBook(String title, int numberOfCopies, Author... authors) {
        Book book = new Book();

        book.setTitle(title);

        Set<Author> authorSet = new HashSet<>(Arrays.asList(authors));
        book.setAuthors(authorSet);

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
