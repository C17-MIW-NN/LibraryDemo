package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Author;
import nl.miwnn.ch17.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.ch17.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.ch17.vincent.librarydemo.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author Vincent Velthuizen
 * Handles all requests related to Authors
 */
@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final ImageService imageService;

    public AuthorController(AuthorRepository authorRepository, ImageService imageService) {
        this.authorRepository = authorRepository;
        this.imageService = imageService;
    }

    @GetMapping("/all")
    public String showAuthorOverview(Model datamodel) {
        datamodel.addAttribute("allAuthors", authorRepository.findAll());
        datamodel.addAttribute("formAuthor", new Author());

        return "authorOverview";
    }

    @PostMapping("/save")
    public String saveOrUpdateAuthor(@ModelAttribute("formAuthor") Author author, BindingResult result,
                                     @RequestParam MultipartFile authorImage) {
        System.out.println(author);

        try {
            imageService.saveImage(authorImage);
            author.setImageURL("/image/" + authorImage.getOriginalFilename());
        } catch (IOException imageError) {
            result.rejectValue("authorImage", "imageNotSaved", "Image not saved");
        }

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
