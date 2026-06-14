package mg.yoan.libtd.repository;

import java.util.List;
import mg.yoan.libtd.model.Sale;
import mg.yoan.libtd.model.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
  List<Sale> findAllByCustomerId(String customerId);

  List<Sale> findAllByStatus(SaleStatus status);
}
