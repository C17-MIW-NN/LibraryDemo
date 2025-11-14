package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Genre;
import nl.miwnn.ch17.vincent.librarydemo.repositories.GenreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Vincent Velthuizen
 * Handle all requests related to Genres
 */

@Controller
@RequestMapping("/genre")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/all")
    public String getAllGenres(Model datamodel) {
        List<Genre> allGenres = genreRepository.findAll();

        allGenres.removeIf(genre -> genre.getBooks().isEmpty());

        datamodel.addAttribute("allGenres", allGenres);

        return "genreOverview";
    }
}
