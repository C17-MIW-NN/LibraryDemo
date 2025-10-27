package nl.miwnn.ch17.vincent.librarydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

/**
 * @author Vincent Velthuizen
 * Handle requests regarding books
 */

@Controller
public class BookController {

    @GetMapping("/books")
    private static String showBookOverview(Model datamodel) {
        datamodel.addAttribute("requesttime", LocalDateTime.now());

        return "bookOverview";
    }

}
