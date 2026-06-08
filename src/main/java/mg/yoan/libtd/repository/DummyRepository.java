package mg.yoan.libtd.repository;

import java.util.List;
import mg.yoan.libtd.PojaGenerated;
import mg.yoan.libtd.repository.model.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@PojaGenerated
@Repository
public interface DummyRepository extends JpaRepository<Dummy, String> {

  @Override
  List<Dummy> findAll();
}
