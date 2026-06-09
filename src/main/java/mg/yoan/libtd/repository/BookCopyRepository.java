package mg.yoan.libtd.repository;

import java.util.List;
import mg.yoan.libtd.model.BookCopy;
import mg.yoan.libtd.model.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {

    List<BookCopy> findAllByBookId(Long bookId);

    List<BookCopy> findAllByStatus(CopyStatus status);
}