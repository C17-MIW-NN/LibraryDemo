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

        makeBook("The Hobbit", 3, "The Hobbit, or There and Back Again is a children's fantasy novel by the English author J. R. R. Tolkien...", "https://images.thenile.io/r1000/9780261103283.jpg", tolkien);
        makeBook("The Lord of the Rings", 5, "The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien...", "https://www.bibdsl.co.uk/imagegallery/bookdata/cd427/9780261103252.JPG", tolkien);
        makeBook("The Name of the Wind", 2, "The Name of the Wind, also referred to as The Kingkiller Chronicle: Day One, is a heroic fantasy novel written by American author Patrick Rothfuss. It is the first book in the ongoing fantasy trilogy The Kingkiller Chronicle, followed by The Wise Man's Fear. It was published on March 27, 2007, by DAW Books.", "https://upload.wikimedia.org/wikipedia/en/5/56/TheNameoftheWind_cover.jpg", rothfuss);
    }

    private Author makeAuthor(String name) {
        Author author = new Author();

        author.setName(name);

        authorRepository.save(author);

        return author;
    }

    private Book makeBook(String title, int numberOfCopies, String description, String coverImageUrl, Author ... authors) {
        Book book = new Book();

        book.setTitle(title);
        book.setDescription(description);
        book.setCoverImageUrl(coverImageUrl);

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
