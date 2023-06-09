package ru.itgirl.libraryproject18n.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.libraryproject18n.dto.GenreWithBookInfoDto;
import ru.itgirl.libraryproject18n.service.GenreService;

@RequestMapping(method = RequestMethod.GET, produces = {"application/json; charset = UTF-8"})
@RestController
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreWithBookInfoDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }
}
