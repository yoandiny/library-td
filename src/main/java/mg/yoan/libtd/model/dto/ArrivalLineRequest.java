package mg.yoan.libtd.model.dto;

import lombok.Data;

@Data
public class ArrivalLineRequest {
  private String bookEditionId;
  private Integer quantity;
}
