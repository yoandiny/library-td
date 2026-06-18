package mg.yoan.libtd.model.dto;

import java.util.UUID;
import lombok.Data;
import mg.yoan.libtd.model.PaymentMethod;

@Data
public class PaymentRequest {
  private UUID saleId;
  private PaymentMethod method;
}
