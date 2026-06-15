package mg.yoan.libtd.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "arrival")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Arrival {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "arrived_at", nullable = false)
  private LocalDateTime arrivedAt;

  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      mappedBy = "arrival",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = jakarta.persistence.FetchType.LAZY)
  private List<ArrivalLine> lines = new ArrayList<>();
}
