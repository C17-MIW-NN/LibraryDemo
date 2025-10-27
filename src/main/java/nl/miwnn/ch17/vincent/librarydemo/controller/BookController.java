package nl.miwnn.ch17.vincent.librarydemo.controller;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Vincent Velthuizen
 * Handle requests regarding books
 */

@Controller
public class BookController {

    @GetMapping("/books")
    private static String showBookOverview(Model datamodel) {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book("The Hobbit"));
        books.add(new Book("The Lord of the Rings"));

        datamodel.addAttribute("books", books);

        return "bookOverview";
    }

}
