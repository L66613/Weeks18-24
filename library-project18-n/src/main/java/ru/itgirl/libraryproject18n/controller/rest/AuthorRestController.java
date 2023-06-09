package ru.itgirl.libraryproject18n.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.libraryproject18n.dto.AuthorCreateDto;
import ru.itgirl.libraryproject18n.dto.AuthorDto;
import ru.itgirl.libraryproject18n.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject18n.service.AuthorService;

@RequestMapping(method = RequestMethod.GET, produces = {"application/json; charset = UTF-8"})
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author")
    AuthorDto getAuthorByName(@RequestParam("name") String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping("/author/v1")
    AuthorDto getAuthorByNameV1(@RequestParam("name") String name) {
        return authorService.getAuthorByNameSql(name);
    }

    @GetMapping("/author/V2")
    AuthorDto getAuthorByNameV2(@RequestParam("name") String name) {
        return authorService.getAuthorByNameV2(name);
    }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody @Valid AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("/author/delete/{id}")
    void updateAuthor(@PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}