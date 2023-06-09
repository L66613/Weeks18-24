package ru.itgirl.libraryproject18n.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject18n.dto.BookCreateDto;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.dto.BookUpdateDto;
import ru.itgirl.libraryproject18n.model.Book;
import ru.itgirl.libraryproject18n.model.Genre;
import ru.itgirl.libraryproject18n.repository.BookRepository;
import ru.itgirl.libraryproject18n.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try to find a book by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException(name);
        }
    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Try to find a book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException(name);
        }
    }

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        log.info("Try to find a book be name {}", name);
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre().getName());
        if (genre == null) {
            genre = new Genre(bookCreateDto.getGenre().getName());
            genre = genreRepository.save(genre);
        }
        log.info("Try to create a book");
        Book book = new Book();
        book.setName(bookCreateDto.getName());
        book.setGenre(genre);
        book = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(book);
        log.info("Book: {}", bookDto.toString() + " created ");
        return bookDto;
    }


    @Override
    public List<BookDto> getAllBooks() {
        log.info("Try to get all books");
        List<Book> books = bookRepository.findAll();
        if (books != null) {
            return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        } else {
            log.error("Books not found");
            throw new NoSuchElementException("No value present");
        }
    }


    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Try to update a book by id");
        Genre genre = genreRepository.findGenreByName(bookUpdateDto.getGenre());
        if (genre == null) {
            genre = Genre.builder()
                    .name(bookUpdateDto.getGenre())
                    .build();
            genre = genreRepository.save(genre);
        }

        Optional<Book> book = bookRepository.findById(bookUpdateDto.getId());
        if (book.isPresent()) {
            book.get().setName(bookUpdateDto.getName());
            book.get().setGenre(genre);
            Book savedBook = bookRepository.save(book.get());
            BookDto bookDto = convertEntityToDto(savedBook);
            log.info("Book with id {}", book.get().getId() + " updated to: " + bookUpdateDto.getName());
            return bookDto;
        } else {
            log.error("Book with this id not found");
            throw new NoSuchElementException("No value present");
        }
    }


    @Override
    public void deleteBook(Long id) {
        log.info("Try to delete a book by id {}", id);
        bookRepository.deleteById(id);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
}
