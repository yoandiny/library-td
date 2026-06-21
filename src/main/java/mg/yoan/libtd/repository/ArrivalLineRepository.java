package mg.yoan.libtd.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ArrivalLineRepository {
    @Query("SELECT COALESCE(SUM(al.quantity), 0) FROM ArrivalLine al WHERE al.bookEdition.id = :bookEditionId")
    Integer sumQuantityByBookEditionId(@Param("bookEditionId") UUID bookEditionId);
}
