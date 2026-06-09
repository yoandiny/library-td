package mg.yoan.libtd.endpoint.rest.controller.health;

import java.util.List;
import mg.yoan.libtd.model.BookCopy;
import mg.yoan.libtd.model.dto.BookCopyRequest;
import mg.yoan.libtd.service.BookCopyService;
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
@RequestMapping("/book-copies")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @PostMapping
    public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopyRequest request) {
        BookCopy created = bookCopyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BookCopy>> getAllBookCopies(
            @RequestParam(required = false) Long bookId) {
        if (bookId != null) {
            return ResponseEntity.ok(bookCopyService.getAllByBook(bookId));
        }
        return ResponseEntity.ok(bookCopyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopy> getBookCopyById(@PathVariable String id) {
        return ResponseEntity.ok(bookCopyService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCopy> updateBookCopy(
            @PathVariable String id, @RequestBody BookCopyRequest request) {
        return ResponseEntity.ok(bookCopyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable String id) {
        bookCopyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}