package mg.yoan.libtd.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SaleLineRequest {
  private UUID bookEditionId;
  private Integer quantity;
}
