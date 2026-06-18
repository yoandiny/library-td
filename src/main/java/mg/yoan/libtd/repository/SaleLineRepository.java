package mg.yoan.libtd.repository;

import java.util.List;
import java.util.UUID;
import mg.yoan.libtd.model.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, UUID> {
  List<SaleLine> findAllBySaleId(UUID saleId);
}
