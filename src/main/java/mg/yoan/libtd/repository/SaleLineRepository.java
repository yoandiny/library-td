package mg.yoan.libtd.repository;

import java.util.UUID;
import mg.yoan.libtd.model.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, UUID> {
  @Query(
      """
    SELECT COALESCE(SUM(sl.quantity), 0) FROM SaleLine sl
    WHERE sl.bookEdition.id = :bookEditionId
    AND sl.sale.status <> mg.yoan.libtd.model.SaleStatus.CANCELLED
""")
  Integer sumQuantityByBookEditionId(@Param("bookEditionId") UUID bookEditionId);
}
