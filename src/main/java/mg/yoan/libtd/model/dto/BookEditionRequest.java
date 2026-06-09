package mg.yoan.libtd.model.dto;

import lombok.Data;

@Data
public class BookEditionRequest {
  private String isbn;
  private Long bookId;
  private String formatId;
  private Double price;
}
