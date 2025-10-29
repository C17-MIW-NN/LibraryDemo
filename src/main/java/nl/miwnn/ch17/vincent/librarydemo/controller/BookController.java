package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/book/edit/{bookId}")
    public String showEditBookForm(@PathVariable("bookId") Long bookId, Model datamodel) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

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
    public String saveOrUpdateBook(@ModelAttribute("formBook") Book bookToBeSaved, BindingResult result) {
        if (!result.hasErrors()) {
            bookRepository.save(bookToBeSaved);
        }

        return "redirect:/book/all";
    }

    @GetMapping("/book/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:/book/all";
    }

}
