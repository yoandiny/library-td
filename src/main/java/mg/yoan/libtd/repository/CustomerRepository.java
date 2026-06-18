package mg.yoan.libtd.repository;

import java.util.UUID;
import mg.yoan.libtd.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {}
