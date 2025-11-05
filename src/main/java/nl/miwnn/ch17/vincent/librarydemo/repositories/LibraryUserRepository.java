package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 */
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findByUsername(String username);
}
