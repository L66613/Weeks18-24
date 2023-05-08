package ru.itgirl.libraryproject18n.service;

import ru.itgirl.libraryproject18n.dto.AuthorCreateDto;
import ru.itgirl.libraryproject18n.dto.AuthorDto;
import ru.itgirl.libraryproject18n.dto.AuthorUpdateDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByName(String name);

    AuthorDto getAuthorByNameSql(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    List<AuthorDto> getAllAuthors();

    void deleteAuthor(Long id);
}