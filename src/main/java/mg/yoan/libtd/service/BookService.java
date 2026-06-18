package mg.yoan.libtd.service;

import java.util.List;
import java.util.UUID;
import mg.yoan.libtd.exception.NotFoundException;
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

    Author author =
        authorRepository
            .findById(bookRequest.getAuthorId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Author with id " + bookRequest.getAuthorId() + " not found"));

    Book book =
        Book.builder()
            .title(bookRequest.getTitle())
            .author(author)
            .isbn(bookRequest.getIsbn())
            .publicationYear(bookRequest.getPublishYear())
            .genre(bookRequest.getGenre())
            .build();

    return bookRepository.save(book);
  }

  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  public Book getById(UUID id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
  }

  public Book update(UUID id, BookRequest bookRequest) {
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));

    Author author =
        authorRepository
            .findById(bookRequest.getAuthorId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Author with id " + bookRequest.getAuthorId() + " not found"));

    book.setTitle(bookRequest.getTitle());
    book.setIsbn(bookRequest.getIsbn());
    book.setGenre(bookRequest.getGenre());
    book.setAuthor(author);
    book.setPublicationYear(bookRequest.getPublishYear());

    return bookRepository.save(book);
  }

  public void delete(UUID id) {
    if (!bookRepository.existsById(id)) {
      throw new NotFoundException("Book with id " + id + " not found");
    }

    bookRepository.deleteById(id);
  }
}
