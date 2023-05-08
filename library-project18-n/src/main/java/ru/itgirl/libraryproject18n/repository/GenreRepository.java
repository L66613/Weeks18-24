package ru.itgirl.libraryproject18n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject18n.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreById(Long id);

    Genre findGenreByName(String name);
}
