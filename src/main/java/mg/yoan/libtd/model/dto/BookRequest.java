package mg.yoan.libtd.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookRequest {
  private String title;
  private UUID authorId;
  private String isbn;
  private Integer publishYear;
  private String genre;
}
