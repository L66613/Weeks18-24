package ru.itgirl.libraryproject18n.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.libraryproject18n.dto.BookCreateDto;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.dto.BookUpdateDto;
import ru.itgirl.libraryproject18n.service.BookService;

@RequestMapping(method = RequestMethod.GET, produces = {"application/json; charset = UTF-8"})
@RestController
@RequiredArgsConstructor
@Controller
@SecurityRequirement(name = "library-users")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/book")
    BookDto getBookByName(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookDto getBookByNameV2(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookDto getBookByNameV3(@RequestParam(value = "name", required = false) String name) {
        return bookService.getByNameV3(name);
    }

    @PostMapping("/book/create")
    BookDto createBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/book/update")
    BookDto updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}