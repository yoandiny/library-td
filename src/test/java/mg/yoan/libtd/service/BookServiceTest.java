package mg.yoan.libtd.service;

import mg.yoan.libtd.model.Author;
import mg.yoan.libtd.model.Book;
import mg.yoan.libtd.model.dto.BookRequest;
import mg.yoan.libtd.repository.AuthorRepository;
import mg.yoan.libtd.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldCreateBook() {

        //given
        BookRequest request = new BookRequest();
        request.setTitle("Spring Boot");
        request.setIsbn("123");
        request.setPublishYear(2025);
        request.setGenre("Tech");
        request.setAuthorId(1L);

        Author author = new Author();
        author.setId(1L);
        author.setFirstName("Benson");
        author.setLastName("Boom");

        Book saved = Book.builder()
                .id("10L")
                .title("Spring Boot")
                .author(author)
                .isbn("123")
                .genre("Tech")
                .publicationYear(2025)
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(saved);

        //when
        Book result = bookService.create(request);

        //then
        assertNotNull(result);
        assertEquals("Spring Boot", result.getTitle());
        assertEquals("10L", result.getId());

        verify(authorRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));

    }



}
