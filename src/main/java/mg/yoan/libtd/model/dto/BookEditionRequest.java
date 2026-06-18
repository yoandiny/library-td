package mg.yoan.libtd.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookEditionRequest {
  private String isbn;
  private UUID bookId;
  private String formatId;
  private Double price;
}
