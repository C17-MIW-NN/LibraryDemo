package nl.miwnn.ch17.vincent.librarydemo.repositories;

import nl.miwnn.ch17.vincent.librarydemo.model.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    List<Copy> findCopiesByPurchasedOn(LocalDate purchasedOn);
}
