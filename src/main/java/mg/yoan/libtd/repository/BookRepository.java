package mg.yoan.libtd.repository;

import java.util.UUID;
import mg.yoan.libtd.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {}
