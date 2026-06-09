package mg.yoan.libtd.endpoint.rest.controller.health;

import java.util.List;
import mg.yoan.libtd.model.BookEdition;
import mg.yoan.libtd.model.dto.BookEditionRequest;
import mg.yoan.libtd.service.BookEditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-editions")
public class BookEditionController {

  private final BookEditionService bookEditionService;

  public BookEditionController(BookEditionService bookEditionService) {
    this.bookEditionService = bookEditionService;
  }

  @PostMapping
  public ResponseEntity<BookEdition> createBookEdition(@RequestBody BookEditionRequest request) {
    BookEdition created = bookEditionService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping
  public ResponseEntity<List<BookEdition>> getAllBookEditions(
      @RequestParam(required = false) Long bookId) {
    if (bookId != null) {
      return ResponseEntity.ok(bookEditionService.getAllByBook(bookId));
    }
    return ResponseEntity.ok(bookEditionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookEdition> getBookEditionById(@PathVariable String id) {
    return ResponseEntity.ok(bookEditionService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookEdition> updateBookEdition(
      @PathVariable String id, @RequestBody BookEditionRequest request) {
    return ResponseEntity.ok(bookEditionService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBookEdition(@PathVariable String id) {
    bookEditionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
