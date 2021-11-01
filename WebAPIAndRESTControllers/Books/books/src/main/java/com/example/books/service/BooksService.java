package com.example.books.service;

import com.example.books.model.dto.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BooksService {
    List<BookDTO> getBooks();

    Optional<BookDTO> getBookById(Long id);

    void deleteBook(Long id);

    Long createBook(BookDTO bookDTO);

    Long updateBook(BookDTO bookDTO, Long bookId);
}
