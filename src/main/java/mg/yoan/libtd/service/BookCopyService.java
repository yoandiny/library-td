package mg.yoan.libtd.service;

import java.util.List;
import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.Book;
import mg.yoan.libtd.model.BookCopy;
import mg.yoan.libtd.model.CopyStatus;
import mg.yoan.libtd.model.dto.BookCopyRequest;
import mg.yoan.libtd.repository.BookCopyRepository;
import mg.yoan.libtd.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;
  private final BookRepository bookRepository;

  public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
    this.bookCopyRepository = bookCopyRepository;
    this.bookRepository = bookRepository;
  }

  public BookCopy create(BookCopyRequest request) {
    Book book =
        bookRepository
            .findById(request.getBookId())
            .orElseThrow(
                () -> new NotFoundException("Book with id " + request.getBookId() + " not found"));

    BookCopy bookCopy =
        BookCopy.builder()
            .book(book)
            .barcode(request.getBarcode())
            .condition(request.getCondition())
            .status(request.getStatus() != null ? request.getStatus() : CopyStatus.AVAILABLE)
            .location(request.getLocation())
            .build();

    return bookCopyRepository.save(bookCopy);
  }

  public List<BookCopy> getAll() {
    return bookCopyRepository.findAll();
  }

  public List<BookCopy> getAllByBook(Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new NotFoundException("Book with id " + bookId + " not found");
    }
    return bookCopyRepository.findAllByBookId(bookId);
  }

  public BookCopy getById(String id) {
    return bookCopyRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("BookCopy with id " + id + " not found"));
  }

  public BookCopy update(String id, BookCopyRequest request) {
    BookCopy bookCopy =
        bookCopyRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("BookCopy with id " + id + " not found"));

    Book book =
        bookRepository
            .findById(request.getBookId())
            .orElseThrow(
                () -> new NotFoundException("Book with id " + request.getBookId() + " not found"));

    bookCopy.setBook(book);
    bookCopy.setBarcode(request.getBarcode());
    bookCopy.setCondition(request.getCondition());
    if (request.getStatus() != null) {
      bookCopy.setStatus(request.getStatus());
    }
    bookCopy.setLocation(request.getLocation());

    return bookCopyRepository.save(bookCopy);
  }

  public void delete(String id) {
    if (!bookCopyRepository.existsById(id)) {
      throw new NotFoundException("BookCopy with id " + id + " not found");
    }
    bookCopyRepository.deleteById(id);
  }
}
