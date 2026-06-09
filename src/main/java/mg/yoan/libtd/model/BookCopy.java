package mg.yoan.libtd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_copy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", nullable = false)
  private Book book;

  @Column(unique = true, length = 100)
  private String barcode;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private CopyCondition condition;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  @Builder.Default
  private CopyStatus status = CopyStatus.AVAILABLE;

  @Column(length = 255)
  private String location;

  @Column(name = "added_at", nullable = false)
  @Builder.Default
  private LocalDateTime addedAt = LocalDateTime.now();
}
