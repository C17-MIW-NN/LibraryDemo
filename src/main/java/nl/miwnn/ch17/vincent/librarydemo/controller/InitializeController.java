package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Author;
import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.model.Copy;
import nl.miwnn.ch17.vincent.librarydemo.model.LibraryUser;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.CopyRepository;
import nl.miwnn.ch17.vincent.librarydemo.service.ImageService;
import nl.miwnn.ch17.vincent.librarydemo.service.LibraryUserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
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
    private final ImageService imageService;
    private final LibraryUserService libraryUserService;

    public InitializeController(AuthorRepository authorRepository,
                                BookRepository bookRepository,
                                CopyRepository copyRepository,
                                ImageService imageService,
                                LibraryUserService libraryUserService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
        this.imageService = imageService;
        this.libraryUserService = libraryUserService;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (bookRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        makeUser("Piet", "PietPW");

        Author sanderson = makeAuthor("Brandon Sanderson", "/image/Brandon_Sanderson.jpg");
        Author rothfuss = makeAuthor("Patrick Rothfuss", "/image/Patrick_Rothfuss.jpg");
        Author tolkien = makeAuthor("J.R.R. Tolkien", "/image/J.R.R._Tolkien.jpg");
        Author maas = makeAuthor("Monica Maas", "/image/Monica_Maas.jpg");

        makeBook("The Hobbit", 3, "The Hobbit, or There and Back Again is a children's fantasy novel by the English author J. R. R. Tolkien...", "https://images.thenile.io/r1000/9780261103283.jpg", tolkien);
        makeBook("The Lord of the Rings", 5, "The Lord of the Rings is an epic high fantasy novel by the English author and scholar J. R. R. Tolkien...", "https://www.bibdsl.co.uk/imagegallery/bookdata/cd427/9780261103252.JPG", tolkien);
        makeBook("The Name of the Wind", 2, "The Name of the Wind, also referred to as The Kingkiller Chronicle: Day One, is a heroic fantasy novel written by American author Patrick Rothfuss. It is the first book in the ongoing fantasy trilogy The Kingkiller Chronicle, followed by The Wise Man's Fear. It was published on March 27, 2007, by DAW Books.", "https://upload.wikimedia.org/wikipedia/en/5/56/TheNameoftheWind_cover.jpg", rothfuss);
        makeBook("The Final Empire", 3, "Mistborn: The Final Empire, also known simply as Mistborn or The Final Empire...", "https://upload.wikimedia.org/wikipedia/en/4/44/Mistborn-cover.jpg", sanderson);
        makeBook("Bobbi doet boodschappen", 1, "Bobbi gaat mee boodschappen doen.", "https://www.bobbi.nl/wp-content/uploads/2022/06/Bobbi-doet-boodschappen.jpg", maas);
    }

    private LibraryUser makeUser(String username, String password) {
        LibraryUser user = new LibraryUser();

        user.setUsername(username);
        user.setPassword(password);

        libraryUserService.saveUser(user);
        return user;
    }

    private Author makeAuthor(String name, String filename) {
        Author author = new Author();

        author.setName(name);

        try {
            saveImage(filename);
            author.setImageURL(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        authorRepository.save(author);

        return author;
    }

    private void saveImage(String filename) throws IOException {
        ClassPathResource imageResource = new ClassPathResource("sampledata" + filename);
        imageService.saveImage(imageResource);
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
