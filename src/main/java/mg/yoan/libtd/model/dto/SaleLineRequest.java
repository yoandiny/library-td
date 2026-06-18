package mg.yoan.libtd.model.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class SaleLineRequest {
  private UUID bookEditionId;
  private Integer quantity;
}
