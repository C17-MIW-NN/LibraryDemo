package nl.miwnn.ch17.vincent.librarydemo.controller;

import com.opencsv.CSVReader;
import nl.miwnn.ch17.vincent.librarydemo.model.*;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.CopyRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.GenreRepository;
import nl.miwnn.ch17.vincent.librarydemo.service.ImageService;
import nl.miwnn.ch17.vincent.librarydemo.service.LibraryUserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Vincent Velthuizen
 * Initialises the database with example data
 */
@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final GenreRepository genreRepository;
    private final ImageService imageService;
    private final LibraryUserService libraryUserService;

    private final Map<String, Author> authorCache;
    private final Map<String, Genre> genreCache;

    public InitializeController(AuthorRepository authorRepository,
                                BookRepository bookRepository,
                                CopyRepository copyRepository,
                                GenreRepository genreRepository,
                                ImageService imageService,
                                LibraryUserService libraryUserService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
        this.genreRepository = genreRepository;
        this.imageService = imageService;
        this.libraryUserService = libraryUserService;

        authorCache = new HashMap<>();
        genreCache = new HashMap<>();
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (bookRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {
        makeUser("Piet", "PietPW");

        loadAuthors("/sampledata/authors.csv");
        loadBooks("/sampledata/books.csv");
    }

    private void loadAuthors(String filename) {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource(filename).getFile()))) {
            // skip header
            reader.skip(1);

            for (String[] authorLine : reader) {
                String name = authorLine[0];
                String imageUrl = authorLine[1];

                Author author = makeAuthor(name, imageUrl);
                authorCache.put(name, author);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void loadBooks(String filename) {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource(filename).getFile()))) {
            // skip header
            reader.skip(1);

            for (String[] bookLine : reader) {
                String title = bookLine[0];
                String description = bookLine[1];
                String genre = bookLine[2];
                String coverImageUrl = bookLine[3];
                int numberOfCopies = Integer.parseInt(bookLine[4]);

                Book book = makeBook(title, description, genre, coverImageUrl, numberOfCopies);

                for (String authorName : bookLine[5].split(", ")) {
                    if (!authorCache.containsKey(authorName)) {
//                        Author author = makeAuthor(authorName, "");
//                        authorCache.put(authorName, author);
                    }
                    book.getAuthors().add(authorCache.get(authorName));
                }

                bookRepository.save(book);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Book makeBook(String title, String description, String genre, String coverImageUrl, int numberOfCopies, Author ... authors) {
        Book book = new Book();

        book.setTitle(title);
        book.setDescription(description);
        book.setCoverImageUrl(coverImageUrl);

        if (!genreCache.containsKey(genre)) {
            makeGenre(genre);
        }
        book.setGenre(genreCache.get(genre));

        Set<Author> authorSet = new HashSet<>(Arrays.asList(authors));
        book.setAuthors(authorSet);

        bookRepository.save(book);

        for (int copyNr = 0; copyNr < numberOfCopies; copyNr++) {
            makeCopy(book, copyNr % 2 == 0);
        }

        return book;
    }

    private Genre makeGenre(String genreShortName) {
        Genre genre = new Genre();

        genre.setShortName(genreShortName);

        genreRepository.save(genre);
        genreCache.put(genreShortName, genre);

        return genre;
    }

    private Copy makeCopy(Book book, boolean available) {
        Copy copy = new Copy(book);

        copy.setAvailable(Boolean.valueOf(available));
        copyRepository.save(copy);

        return copy;
    }
}
