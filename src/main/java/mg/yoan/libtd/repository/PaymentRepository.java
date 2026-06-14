package mg.yoan.libtd.repository;

import java.util.Optional;
import mg.yoan.libtd.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
  Optional<Payment> findBySaleId(String saleId);
}
