package ru.itgirl.libraryproject18n.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject18n.dto.AuthorCreateDto;
import ru.itgirl.libraryproject18n.dto.AuthorDto;
import ru.itgirl.libraryproject18n.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject18n.model.Author;
import ru.itgirl.libraryproject18n.model.Book;
import ru.itgirl.libraryproject18n.repository.AuthorRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;
    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByName(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByName(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameNotFound(){
        String name = "John";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByName(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testGetAuthorByNameSql(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByNameSql(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameSql(name);
        verify(authorRepository).findAuthorByNameSql(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByNameSqlNotFound(){
        String name = "John";
        when(authorRepository.findAuthorByNameSql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameSql(name));
        verify(authorRepository).findAuthorByNameSql(name);
    }

    @Test
    public void testGetAllAuthors(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);
        List<AuthorDto> authorDtoList = authorService.getAllAuthors();

        verify(authorRepository).findAll();
        Assertions.assertNotNull(authorDtoList);
        Assertions.assertEquals(authors.size(), authorDtoList.size());
        AuthorDto authorDto = authorDtoList.get(0);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testCreateAuthor(){
        AuthorCreateDto authorCreation = new AuthorCreateDto("John", "Doe");
        Set<Book> books = new HashSet<>();
        Author author = new Author(1L, authorCreation.getName(), authorCreation.getSurname(), books);

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto authorDto = authorService.createAuthor(authorCreation);

        verify(authorRepository).save(any(Author.class));

        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
        Assertions.assertEquals(author.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testUpdateAuthor(){
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = new HashSet<>();
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(id, "Jane", "Austine");
        Author author = new Author(id, name, surname, books);
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);

        AuthorDto authorDto = authorService.updateAuthor(authorUpdateDto);
        verify(authorRepository).findById(id);
        verify(authorRepository).save(author);
        Assertions.assertNotNull(authorDto);
        Assertions.assertEquals(authorUpdateDto.getId(), authorDto.getId());
        Assertions.assertEquals(authorUpdateDto.getName(), authorDto.getName());
        Assertions.assertEquals(authorUpdateDto.getSurname(), authorDto.getSurname());
    }

    @Test
    public void testDeleteAuthor(){
        Long id = 1L;

        authorService.deleteAuthor(id);
        verify(authorRepository).deleteById(id);
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }
}