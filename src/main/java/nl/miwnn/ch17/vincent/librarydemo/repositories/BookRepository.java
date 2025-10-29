package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
