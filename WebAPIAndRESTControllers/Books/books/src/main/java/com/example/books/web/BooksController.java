package com.example.books.web;

import com.example.books.model.dto.BookDTO;
import com.example.books.service.BooksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    //called on http://localhost:8080/books
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = booksService.getBooks();

        return ResponseEntity.ok(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        Optional<BookDTO> book = booksService.getBookById(id);

        if(book.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(book.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("id") Long id) {
        booksService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable("id") long bookId,
                                          @RequestBody BookDTO bookDTO, UriComponentsBuilder builder) {
        Long updatedBookId = booksService.updateBook(bookDTO, bookId);
        return updatedBookId != null ?
                ResponseEntity.created(builder.path("/books/{id}").buildAndExpand(updatedBookId).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<BookDTO> create(
            @RequestBody BookDTO bookDTO,
            UriComponentsBuilder builder) {
        //http://localhost:8080/books/id
        long bookId = booksService.createBook(bookDTO);

        URI location = builder.path("/books/{id}").
                buildAndExpand(bookId).toUri();

        return ResponseEntity.
                created(location).
                build();

    }
}
