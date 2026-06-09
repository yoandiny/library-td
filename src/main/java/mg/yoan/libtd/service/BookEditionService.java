package mg.yoan.libtd.service;

import java.util.List;
import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.Book;
import mg.yoan.libtd.model.BookEdition;
import mg.yoan.libtd.model.Format;
import mg.yoan.libtd.model.dto.BookEditionRequest;
import mg.yoan.libtd.repository.BookEditionRepository;
import mg.yoan.libtd.repository.BookRepository;
import mg.yoan.libtd.repository.FormatRepository;
import org.springframework.stereotype.Service;

@Service
public class BookEditionService {

  private final BookEditionRepository bookEditionRepository;
  private final BookRepository bookRepository;
  private final FormatRepository formatRepository;

  public BookEditionService(
      BookEditionRepository bookEditionRepository,
      BookRepository bookRepository,
      FormatRepository formatRepository) {
    this.bookEditionRepository = bookEditionRepository;
    this.bookRepository = bookRepository;
    this.formatRepository = formatRepository;
  }

  public BookEdition create(BookEditionRequest request) {
    Book book = findBook(request.getBookId());
    Format format = findFormat(request.getFormatId());

    BookEdition bookEdition =
        BookEdition.builder()
            .isbn(request.getIsbn())
            .book(book)
            .format(format)
            .price(request.getPrice())
            .build();

    return bookEditionRepository.save(bookEdition);
  }

  public List<BookEdition> getAll() {
    return bookEditionRepository.findAll();
  }

  public List<BookEdition> getAllByBook(Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new NotFoundException("Book with id " + bookId + " not found");
    }
    return bookEditionRepository.findAllByBookId(bookId);
  }

  public BookEdition getById(String id) {
    return bookEditionRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("BookEdition with id " + id + " not found"));
  }

  public BookEdition update(String id, BookEditionRequest request) {
    BookEdition bookEdition = getById(id);

    bookEdition.setIsbn(request.getIsbn());
    bookEdition.setBook(findBook(request.getBookId()));
    bookEdition.setFormat(findFormat(request.getFormatId()));
    bookEdition.setPrice(request.getPrice());

    return bookEditionRepository.save(bookEdition);
  }

  public void delete(String id) {
    if (!bookEditionRepository.existsById(id)) {
      throw new NotFoundException("BookEdition with id " + id + " not found");
    }
    bookEditionRepository.deleteById(id);
  }

  private Book findBook(Long bookId) {
    return bookRepository
        .findById(bookId)
        .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
  }

  private Format findFormat(String formatId) {
    if (formatId == null) {
      return null;
    }
    return formatRepository
        .findById(formatId)
        .orElseThrow(() -> new NotFoundException("Format with id " + formatId + " not found"));
  }
}
