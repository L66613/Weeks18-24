package ru.itgirl.libraryproject18n.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject18n.dto.AuthorCreateDto;
import ru.itgirl.libraryproject18n.dto.AuthorDto;
import ru.itgirl.libraryproject18n.dto.AuthorUpdateDto;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.model.Author;
import ru.itgirl.libraryproject18n.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getAuthorByName(String name) {
        log.info("Try to find author by name {}, name");
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value found");
        }
    }

    @Override
    public AuthorDto getAuthorByNameSql(String name) {
        log.info("Try to find author by name {}, name");
        Optional<Author> author = authorRepository.findAuthorByNameSql(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("Not value found");
        }
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
        });
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findOne(specification);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", name, authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new IllegalStateException("Author not found");
        }
    }


    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Try to create an author", authorCreateDto.toString());
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Author: {}", authorDto.toString() + " created");
        return authorDto;
    }


    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Try to update an author");
        Optional<Author> author = authorRepository.findById(authorUpdateDto.getId());
        if (author.isPresent()) {
            Author author1 = author.get();
            author1.setName(authorUpdateDto.getName());
            author1.setSurname(authorUpdateDto.getSurname());
            Author savedAuthor = authorRepository.save(author1);
            AuthorDto authorDto = convertEntityToDto(savedAuthor);
            log.info("Author: {}", " updated to " + authorUpdateDto);
            return authorDto;
        } else {
            log.error("Author with this id not found");
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        log.info("Try to get all authors");
        List<Author> authors = authorRepository.findAll();
        if (authors != null) {
            return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        } else {
            log.error("Authors not found");
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Try to delete an author by id {}", id);
        authorRepository.deleteById(id);
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }

        AuthorDto authorDto = AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
        return authorDto;
    }
}