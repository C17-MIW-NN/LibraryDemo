package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
