package mg.yoan.libtd.repository;

import java.util.List;
import mg.yoan.libtd.model.BookEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEditionRepository
    extends JpaRepository<BookEdition, String>, JpaSpecificationExecutor<BookEdition> {

  List<BookEdition> findAllByBookId(Long bookId);
}
