package mg.yoan.libtd.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class SaleRequest {
  private String customerId;
  private List<SaleLineRequest> lines;
}
