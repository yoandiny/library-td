package mg.yoan.libtd.repository;

import java.util.Optional;
import java.util.UUID;

import mg.yoan.libtd.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
  Optional<Payment> findBySaleId(UUID saleId);
}
