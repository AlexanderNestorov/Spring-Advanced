package com.example.books.service.impl;

import com.example.books.model.dto.AuthorDTO;
import com.example.books.model.dto.BookDTO;
import com.example.books.model.entity.BookEntity;
import com.example.books.repository.BookRepository;
import com.example.books.service.BooksService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksServiceImpl implements BooksService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BooksServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<BookDTO> getBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::asBook)
                .collect(Collectors.toList());
    }

    private BookDTO asBook(BookEntity book) {
        BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
        AuthorDTO authorDTO = modelMapper.map(book.getAuthor(),AuthorDTO.class);

        bookDTO.setAuthor(authorDTO);

        return bookDTO;
    }
}
