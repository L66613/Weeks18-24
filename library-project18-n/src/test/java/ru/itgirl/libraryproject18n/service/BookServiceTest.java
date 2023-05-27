package ru.itgirl.libraryproject18n.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.model.Author;
import ru.itgirl.libraryproject18n.model.Book;
import ru.itgirl.libraryproject18n.model.Genre;
import ru.itgirl.libraryproject18n.repository.BookRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void TestGetByNameV1() {
        Long id = 1L;
        String name = "Vedmak";
        Genre genre = new Genre();
        Set<Author> authorSet = new HashSet<>();

        Book book = new Book(id, name, genre, authorSet);

        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV1(name);
        verify(bookRepository).findBookByName(name);

        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
        Assertions.assertEquals(bookDto.getGenre(), book.getGenre());
    }
}
