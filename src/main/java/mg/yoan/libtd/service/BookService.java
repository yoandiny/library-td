package mg.yoan.libtd.service;

import java.util.List;
import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.Author;
import mg.yoan.libtd.model.Book;
import mg.yoan.libtd.model.BookEdition;
import mg.yoan.libtd.model.dto.BookRequest;
import mg.yoan.libtd.repository.AuthorRepository;
import mg.yoan.libtd.repository.BookEditionRepository;
import mg.yoan.libtd.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final BookEditionRepository bookEditionRepository;

  public BookService(
          BookRepository bookRepository,
          AuthorRepository authorRepository,
          BookEditionRepository bookEditionRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.bookEditionRepository = bookEditionRepository;
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

  public Book getById(Long id) {
    return bookRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
  }

  public Book update(Long id, BookRequest bookRequest) {
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

  public void delete(Long id) {
    if (!bookRepository.existsById(id)) {
      throw new NotFoundException("Book with id " + id + " not found");
    }

    bookRepository.deleteById(id);
  }

  public BookEdition getEditionOfBook(Long bookId, String editionId) {
    if (!bookRepository.existsById(bookId)) {
      throw new NotFoundException("Book with id " + bookId + " not found");
    }

    BookEdition edition =
            bookEditionRepository
                    .findById(editionId)
                    .orElseThrow(
                            () -> new NotFoundException("BookEdition with id " + editionId + " not found"));

    if (!edition.getBook().getId().equals(bookId)) {
      throw new NotFoundException(
              "BookEdition with id " + editionId + " does not belong to Book with id " + bookId);
    }

    return edition;
  }
}