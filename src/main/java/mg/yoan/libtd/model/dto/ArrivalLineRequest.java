package mg.yoan.libtd.model.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ArrivalLineRequest {
  private UUID bookEditionId;
  private Integer quantity;
}
