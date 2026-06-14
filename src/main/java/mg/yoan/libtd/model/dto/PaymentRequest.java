package mg.yoan.libtd.model.dto;

import lombok.Data;
import mg.yoan.libtd.model.PaymentMethod;

@Data
public class PaymentRequest {
    private String saleId;
    private PaymentMethod method;
}