package nl.miwnn.ch17.vincent.librarydemo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vincent Velthuizen
 * Test methods from the Book class
 */
class BookTest {

    @Test
    @DisplayName("test number of available copies when no copies are available")
    void testNumberOfAvailableCopiesWhenNoCopiesAreAvailable() {
        // Arrange
        int expectedAvailableCopies = 0;

        Book book = new Book();

        book.setCopies(new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            Copy copy = new Copy(book);
            copy.setAvailable(false);
            book.getCopies().add(copy);
        }

        // Act
        int availableCopies = book.getAvailableNumberOfCopies();

        // Assert
        assertEquals(expectedAvailableCopies, availableCopies);
    }

    @Test
    @DisplayName("test number of available copies when all copies are available")
    void testNumberOfAvailableCopiesWhenAllCopiesAreAvailable() {
        // Arrange
        int expectedAvailableCopies = 3;

        Book book = new Book();

        book.setCopies(new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            Copy copy = new Copy(book);
            book.getCopies().add(copy);
        }

        // Act
        int availableCopies = book.getAvailableNumberOfCopies();

        // Assert
        assertEquals(expectedAvailableCopies, availableCopies);
    }

    @Test
    @DisplayName("test number of available copies when some copies are available")
    void testNumberOfAvailableCopiesWhenSomeCopiesAreAvailable() {
        // Arrange
        int expectedAvailableCopies = 3;

        Book book = new Book();

        book.setCopies(new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            Copy copy = new Copy(book);
            copy.setAvailable(false);
            book.getCopies().add(copy);
        }

        for (int i = 0; i < 3; i++) {
            Copy copy = new Copy(book);
            book.getCopies().add(copy);
        }

        // Act
        int availableCopies = book.getAvailableNumberOfCopies();

        // Assert
        assertEquals(expectedAvailableCopies, availableCopies);
    }
}