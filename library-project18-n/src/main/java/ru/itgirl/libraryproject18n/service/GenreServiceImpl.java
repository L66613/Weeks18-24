package ru.itgirl.libraryproject18n.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject18n.dto.BookWithAuthorDto;
import ru.itgirl.libraryproject18n.dto.GenreWithBookInfoDto;
import ru.itgirl.libraryproject18n.model.Genre;
import ru.itgirl.libraryproject18n.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;


    @Override
    public GenreWithBookInfoDto getGenreById(Long id) {
        log.info("Try to find genre by id {}", id);
        Genre genre = genreRepository.findGenreById(id).orElse(null);
        if (genre != null) {
            List<BookWithAuthorDto> bookWithAuthorDtoList = genre.getBooks()
                    .stream()
                    .map(book -> BookWithAuthorDto.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .author(book.getAuthors()
                                    .stream().map(author -> author.getName() + " " + author.getSurname())
                                    .collect(Collectors.joining(", ")))
                            .build())
                    .collect(Collectors.toList());

            GenreWithBookInfoDto genreWithBookInfoDto = new GenreWithBookInfoDto();
            genreWithBookInfoDto.setId(genre.getId());
            genreWithBookInfoDto.setBooks(bookWithAuthorDtoList);
            genreWithBookInfoDto.setName(genre.getName());
            log.info("Genre: {}", genreWithBookInfoDto.toString());
            return genreWithBookInfoDto;
        } else {
            log.error("Genre with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }
}