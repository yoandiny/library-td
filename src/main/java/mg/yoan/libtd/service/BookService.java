package mg.yoan.libtd.service;

import jakarta.persistence.EntityNotFoundException;
import mg.yoan.libtd.model.Author;
import mg.yoan.libtd.model.Book;
import mg.yoan.libtd.model.dto.BookRequest;
import mg.yoan.libtd.repository.AuthorRepository;
import mg.yoan.libtd.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book create(BookRequest bookRequest) {

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Author not found"));

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(author)
                .isbn(bookRequest.getIsbn())
                .publicationYear(bookRequest.getPublishYear())
                .genre(bookRequest.getGenre())
                .build();

        return bookRepository.save(book);
    }





}
