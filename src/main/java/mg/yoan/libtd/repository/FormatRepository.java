package mg.yoan.libtd.repository;

import mg.yoan.libtd.model.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FormatRepository extends JpaRepository<Format, UUID> {}
