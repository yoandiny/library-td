package mg.yoan.libtd.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookEditionRequest {
  private String isbn;
  private UUID bookId;
  private UUID formatId;
  private Double price;
}
