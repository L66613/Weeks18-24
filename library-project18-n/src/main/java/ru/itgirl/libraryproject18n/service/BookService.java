package ru.itgirl.libraryproject18n.service;

import ru.itgirl.libraryproject18n.dto.BookCreateDto;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    BookDto getByNameV1(String name);
    BookDto getByNameV2(String name);
    BookDto getByNameV3(String name);
    BookDto createBook(BookCreateDto bookCreateDto);
    List<BookDto> getAllBooks();
    BookDto updateBook(BookUpdateDto bookUpdateDto);
    void deleteBook(Long id);
}