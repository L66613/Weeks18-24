package ru.itgirl.libraryproject18n.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject18n.dto.*;
import ru.itgirl.libraryproject18n.model.Author;
import ru.itgirl.libraryproject18n.model.Book;
import ru.itgirl.libraryproject18n.model.Genre;
import ru.itgirl.libraryproject18n.repository.BookRepository;
import ru.itgirl.libraryproject18n.repository.GenreRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    GenreRepository genreRepository;

    @Test
    public void testGetByNameV1() {
        Long id = 1L;
        String name = "The Witcher";
        Genre genre = new Genre();
        Set<Author> authorSet = new HashSet<>();
        Book book = new Book(id, name, genre, authorSet);
        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV1(name);

        verify(bookRepository).findBookByName(name);

        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetByNameV1NotFound() {
        String name = "The Witcher";
        when(bookRepository.findBookByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> bookService.getByNameV1(name));
        verify(bookRepository).findBookByName(name);
    }

    @Test
    public void testGetByNameV2() {
        Long id = 1L;
        String name = "The Witcher";
        Genre genre = new Genre();
        Set<Author> authorSet = new HashSet<>();
        Book book = new Book(id, name, genre, authorSet);
        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getByNameV2(name);

        verify(bookRepository).findBookByNameBySql(name);

        Assertions.assertEquals(bookDto.getId(), book.getId());
        Assertions.assertEquals(bookDto.getName(), book.getName());
    }

    @Test
    public void testGetByNameV2NotFound() {
        String name = "The Witcher";
        when(bookRepository.findBookByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> bookService.getByNameV2(name));
        verify(bookRepository).findBookByNameBySql(name);
    }

    @Test
    public void testCreateBook() {
        Genre genre = new Genre("Fantasy");
        GenreCreateDto genreCreateDto = new GenreCreateDto("Fantasy");
        BookCreateDto bookCreation = new BookCreateDto("The Witcher", genre);
        Set<Author> authors = new HashSet<>();
        Book book = new Book(1L, "The Witcher", genre, authors);

        when(genreRepository.findGenreByName(genreCreateDto.getName())).thenReturn(genre);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.createBook(bookCreation);
        verify(genreRepository).findGenreByName(genre.getName());
        verify(bookRepository).save(any(Book.class));

        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }

    @Test
    public void testGetAllBooks() {
        Long id = 1L;
        String name = "The Witcher";
        Genre genre = new Genre();
        Set<Author> authorSet = new HashSet<>();
        Book book = new Book(id, name, genre, authorSet);
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> bookDtoList = bookService.getAllBooks();

        verify(bookRepository).findAll();
        Assertions.assertNotNull(bookDtoList);
        Assertions.assertEquals(books.size(), bookDtoList.size());
        BookDto bookDto = bookDtoList.get(0);
        Assertions.assertEquals(book.getId(), bookDto.getId());
        Assertions.assertEquals(book.getName(), bookDto.getName());
    }

    @Test
    public void testUpdateBook() {
        Long id = 1L;
        String name = "The Witcher";
        Genre genre = new Genre("Fantasy");
        Set<Author> authors = new HashSet<>();
        Book book = new Book(id, name, genre, authors);
        BookUpdateDto bookUpdateDto = new BookUpdateDto(id, "The Witcher: Blood of Elves", "Fantasy");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(genreRepository.findGenreByName(bookUpdateDto.getGenre())).thenReturn(genre);

        BookDto bookDto = bookService.updateBook(bookUpdateDto);
        verify(bookRepository).findById(id);
        verify(bookRepository).save(book);
        verify(genreRepository).findGenreByName(bookUpdateDto.getGenre());
        Assertions.assertNotNull(bookDto);
        Assertions.assertEquals(bookUpdateDto.getId(), bookDto.getId());
        Assertions.assertEquals(bookUpdateDto.getName(), bookDto.getName());
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L;

        bookService.deleteBook(id);
        verify(bookRepository).deleteById(id);
    }
}
