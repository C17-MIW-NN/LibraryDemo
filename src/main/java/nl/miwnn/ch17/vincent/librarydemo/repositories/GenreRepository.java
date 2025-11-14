package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.Book;
import nl.miwnn.ch17.vincent.librarydemo.model.Genre;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByFullNameIgnoreCase(String fullName);
    boolean existsByShortNameIgnoreCase(String shortName);

    Optional<Genre> findByShortNameIgnoreCase(String shortName);
}
