package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Author;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vincent Velthuizen
 * Handles all requests related to Authors
 */
@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/all")
    public String showAuthorOverview(Model datamodel) {
        datamodel.addAttribute("allAuthors", authorRepository.findAll());
        datamodel.addAttribute("formAuthor", new Author());

        return "authorOverview";
    }

    @PostMapping("/save")
    public String saveOrUpdateAuthor(@ModelAttribute("formAuthor") Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/author/all";
        }

        authorRepository.save(author);
        return "redirect:/author/all";
    }

    @GetMapping("/delete/{authorId}")
    public String deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorRepository.deleteById(authorId);
        return "redirect:/author/all";
    }
}
