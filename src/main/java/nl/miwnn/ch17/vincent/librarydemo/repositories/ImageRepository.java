package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFileName(String fileName);
    boolean existsByFileName(String fileName);
}
