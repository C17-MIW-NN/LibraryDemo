package nl.miwnn.ch17.vincent.librarydemo.service;

import nl.miwnn.ch17.vincent.librarydemo.model.LibraryUser;
import nl.miwnn.ch17.vincent.librarydemo.repositories.LibraryUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 */

@Service
public class LibraryUserService implements UserDetailsService {
    private final LibraryUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LibraryUserService(LibraryUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found."));
    }

    public void saveUser(LibraryUser libraryUser) {
        libraryUser.setPassword(passwordEncoder.encode(libraryUser.getPassword()));
        repository.save(libraryUser);
    }
}
