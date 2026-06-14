package mg.yoan.libtd.repository;

import java.util.List;
import mg.yoan.libtd.model.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, String> {
    List<SaleLine> findAllBySaleId(String saleId);
}