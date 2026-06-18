package mg.yoan.libtd.repository;

import java.util.List;
import java.util.UUID;
import mg.yoan.libtd.model.Sale;
import mg.yoan.libtd.model.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
  List<Sale> findAllByCustomerId(UUID customerId);

  List<Sale> findAllByStatus(SaleStatus status);
}
