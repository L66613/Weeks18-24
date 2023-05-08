package ru.itgirl.libraryproject18n.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject18n.dto.AuthorDto;
import ru.itgirl.libraryproject18n.dto.BookCreateDto;
import ru.itgirl.libraryproject18n.dto.BookDto;
import ru.itgirl.libraryproject18n.dto.BookUpdateDto;
import ru.itgirl.libraryproject18n.model.Author;
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
        log.info("Try to find a book by name {}, name");
        Optional<Book> book = bookRepository.findBookByName(name);
        if(book.isPresent()){
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }else{
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException(name);
        }
    }
//    @Override
//    public BookDto getByNameV2(String name) {
//        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
//        return convertEntityToDto(book);
//    }
    @Override
    public BookDto getByNameV2(String name) {
        log.info("Try to find a book by name {}, name");
        Optional<Book> book = bookRepository.findBookByName(name);
        if(book.isPresent()){
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }else{
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException(name);
        }
    }
//    @Override
//    public BookDto getByNameV3(String name) {
//        Specification<Book> specification = Specification.where(new Specification<Book>() {
//            @Override
//            public Predicate toPredicate(Root<Book> root,
//                                         CriteriaQuery<?> query,
//                                         CriteriaBuilder cb) {
//                return cb.equal(root.get("name"), name);
//            }
//        });
//
//        Book book = bookRepository.findOne(specification).orElseThrow();
//        return convertEntityToDto(book);
//    }
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
        if (book.isPresent()){
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }
//    @Override
//    public BookDto createBook(BookCreateDto bookCreateDto){
//        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre().getName());
//        if(genre == null){
//            genre = new Genre(bookCreateDto.getGenre().getName());
//            genre = genreRepository.save(genre);
//        }
//        Book book = Book.builder()
//                .name(bookCreateDto.getName())
//                .genre(genre)
//                .build();
//        book = bookRepository.save(book);
//        BookDto bookDto = convertEntityToDto(book);
//        return bookDto;
//    }
    @Override
    public BookDto createBook(BookCreateDto bookCreateDto){
        log.info("Try to create a book");
        Book book = bookRepository.findBookByName(bookCreateDto.getName()).orElse(null);
        Genre genre = genreRepository.findGenreByName(bookCreateDto.getGenre().getName());
        if(book == null) {
            if (genre == null) {
                genre = new Genre(bookCreateDto.getGenre().getName().toString());
                genre = genreRepository.save(genre);
            }
            Book book1 = Book.builder()
                    .name(bookCreateDto.getName())
                    .genre(genre)
                    .build();
            book1 = bookRepository.save(book1);
            BookDto bookDto = convertEntityToDto(book1);
            log.info("Book: {}", bookCreateDto + " created ");
            return bookDto;
        }else {
            log.error("Book already exists");
            throw new NoSuchElementException("No value present");
        }
    }

//    @Override
//    public List<BookDto> getAllBooks() {
//        List<Book> books = bookRepository.findAll();
//        return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
//    }
    @Override
    public List<BookDto> getAllBooks() {
        log.info("Try to find all books");
        List<Book> books = bookRepository.findAll();
        if (books != null){
            return books.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        }else{
            log.error("Books not found");
            throw new NoSuchElementException("No value present");
        }
    }
//    @Override
//    public BookDto updateBook(BookUpdateDto bookUpdateDto){
//        Genre genre = genreRepository.findGenreByName(bookUpdateDto.getGenre());
//        if (genre == null){
//            genre = Genre.builder()
//                    .name(bookUpdateDto.getGenre())
//                    .build();
//            genre = genreRepository.save(genre);
//        }
//        Book book = bookRepository.findBookById(bookUpdateDto.getId()).orElseThrow();
//        book.setName(bookUpdateDto.getName());
//        book.setGenre(genre);
//        Book savedBook = bookRepository.save(book);
//        BookDto bookDto = convertEntityToDto(savedBook);
//        return bookDto;
//    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto){
        log.info("Try to update a book");
        Book bookId = bookRepository.findBookById(bookUpdateDto.getId()).orElse(null);
        Genre genre = genreRepository.findGenreByName(bookUpdateDto.getGenre());
        if(bookId != null) {
            if (genre == null) {
                genre = Genre.builder()
                        .name(bookUpdateDto.getGenre())
                        .build();
                genre = genreRepository.save(genre);
            }
            Book book = bookRepository.findBookById(bookUpdateDto.getId()).orElse(null);
            book.setName(bookUpdateDto.getName());
            book.setGenre(genre);
            Book savedBook = bookRepository.save(book);
            BookDto bookDto = convertEntityToDto(savedBook);
            log.info("Book: {}", " updated to " + bookUpdateDto);
            return bookDto;
        }else {
            log.error("Book with this id not found");
            throw new NoSuchElementException("No value present");
        }
    }

//    @Override
//    public void deleteBook(Long id){
//        bookRepository.deleteById(id);
//    }
    @Override
    public void deleteBook(Long id){
        log.info("Try to delete a book by id {}", id);
        Optional<Book> book = bookRepository.findBookById(id);
        if(book.isPresent()){
            BookDto bookDto = convertEntityToDto(book.get());
            bookRepository.deleteById(id);
            log.info("Book: {}", bookDto.toString() + " was deleted");
        }else{
            log.error("Book with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
}
