package ru.itgirl.libraryproject18n.service;

import ru.itgirl.libraryproject18n.dto.GenreWithBookInfoDto;

public interface GenreService {
    GenreWithBookInfoDto getGenreById(Long id);
}
