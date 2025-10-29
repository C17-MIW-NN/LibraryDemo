package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle requests regarding books
 */

@Controller
public class BookController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping({"/book/all", "/"})
    public String showBookOverview(Model datamodel) {
        datamodel.addAttribute("books", bookRepository.findAll());

        return "bookOverview";
    }

    @GetMapping("/book/add")
    public String showBookForm(Model datamodel) {
        return showBookForm(datamodel, new Book());
    }

    @GetMapping("/book/edit/{title}")
    public String showEditBookForm(@PathVariable("title") String title, Model datamodel) {
        Optional<Book> optionalBook = bookRepository.findByTitle(title);

        if (optionalBook.isPresent()) {
            return showBookForm(datamodel, optionalBook.get());
        }

        return "redirect:/book/all";
    }

    private String showBookForm(Model datamodel, Book book) {
        datamodel.addAttribute("formBook", book);
        datamodel.addAttribute("allAuthors", authorRepository.findAll());

        return "bookForm";
    }

    @PostMapping("/book/save")
    public String saveOrUpdateBook(@ModelAttribute("formBook") Book bookToBeSaved,
                                   BindingResult result,
                                   Model datamodel) {
        Optional<Book> bookWithSameTitle = bookRepository.findByTitle(bookToBeSaved.getTitle());

        if (bookWithSameTitle.isPresent() && !bookWithSameTitle.get().getBookId().equals(bookToBeSaved.getBookId())) {
            result.addError(new FieldError("book", "title",
                    "this title is already in use by another book"));
        }

        if (result.hasErrors()) {
            return showBookForm(datamodel, bookToBeSaved);
        }

        bookRepository.save(bookToBeSaved);
        return "redirect:/book/all";
    }

    @GetMapping("/book/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:/book/all";
    }

    @GetMapping("/book/detail/{title}")
    public String showBookDetailpage(@PathVariable("title") String title, Model datamodel) {
        Optional<Book> bookToShow = bookRepository.findByTitle(title);

        if (bookToShow.isEmpty()) {
            return "redirect:/book/all";
        }

        datamodel.addAttribute("book", bookToShow.get());

        return "bookDetails";
    }

}
