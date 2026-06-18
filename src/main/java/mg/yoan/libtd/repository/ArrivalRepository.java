package mg.yoan.libtd.repository;

import mg.yoan.libtd.model.Arrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArrivalRepository extends JpaRepository<Arrival, UUID> {}
