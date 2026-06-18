package mg.yoan.libtd.model.dto;

import lombok.Data;
import mg.yoan.libtd.model.PaymentMethod;

import java.util.UUID;

@Data
public class PaymentRequest {
  private UUID saleId;
  private PaymentMethod method;
}
