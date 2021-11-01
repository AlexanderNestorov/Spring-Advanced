package com.example.books.service.impl;

import com.example.books.model.dto.AuthorDTO;
import com.example.books.model.dto.BookDTO;
import com.example.books.model.entity.AuthorEntity;
import com.example.books.model.entity.BookEntity;
import com.example.books.repository.AuthorRepository;
import com.example.books.repository.BookRepository;
import com.example.books.service.BooksService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksServiceImpl implements BooksService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    public BooksServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }


    @Override
    public List<BookDTO> getBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::asBook)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository
                .findById(id)
                .map(this::asBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {

        AuthorEntity author = authorRepository
                .findByName(bookDTO.getAuthor().getName())
                .orElseGet(() -> new AuthorEntity().setName(bookDTO.getAuthor().getName()));

        BookEntity newBook = new BookEntity()
                .setAuthor(author)
                .setIsbn(bookDTO.getIsbn())
                .setTitle(bookDTO.getTitle());

        return bookRepository.save(newBook).getId();
    }

    @Override
    public Long updateBook(BookDTO bookDTO, Long bookId) {
        AuthorEntity author = authorRepository.
                findByName(bookDTO.getAuthor().getName()).
                orElseGet(() -> new AuthorEntity().setName(bookDTO.getAuthor().getName()));

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElse(null);
        if (bookEntity == null) {
            return null;
        }

        bookEntity.setTitle(bookDTO.getTitle())
                .setIsbn(bookDTO.getIsbn())
                .setAuthor(author);
        return bookRepository.save(bookEntity).getId();
    }


    private BookDTO asBook(BookEntity book) {
        BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
        AuthorDTO authorDTO = modelMapper.map(book.getAuthor(),AuthorDTO.class);

        bookDTO.setAuthor(authorDTO);

        return bookDTO;
    }
}
