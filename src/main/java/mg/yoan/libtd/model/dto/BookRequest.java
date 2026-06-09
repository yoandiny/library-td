package mg.yoan.libtd.model.dto;

import lombok.Data;

@Data
public class BookRequest {
  private String title;
  private Long authorId;
  private String isbn;
  private Integer publishYear;
  private String genre;
}
