package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.model.Copy;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle requests regarding copies
 */
@Controller
@RequestMapping("/copy")
public class CopyController {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public CopyController(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @GetMapping("/new/{bookId}")
    private String createNewCopy(@PathVariable("bookId") Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            return "redirect:/book/all";
        }

        Copy copy = new Copy(optionalBook.get());
        copyRepository.save(copy);

        return redirectToBookDetailpage(copy);
    }

    @GetMapping("/borrow/{copyId}")
    private String makeCopyUnavailable(@PathVariable("copyId") Long copyId) {
        return setCopyAvailability(copyId, false);
    }

    @GetMapping("/return/{copyId}")
    private String makeCopyAvailable(@PathVariable("copyId") Long copyId) {
        return setCopyAvailability(copyId, true);
    }

    private String setCopyAvailability(Long copyId, boolean available) {
        Optional<Copy> optionalCopy = copyRepository.findById(copyId);

        if (optionalCopy.isEmpty()) {
            return "redirect:/";
        }

        Copy copy = optionalCopy.get();
        copy.setAvailable(available);
        copyRepository.save(copy);

        return redirectToBookDetailpage(copy);
    }

    private String redirectToBookDetailpage(Copy copy) {
        return "redirect:/book/detail/" + copy.getBook().getTitle();
    }

}
