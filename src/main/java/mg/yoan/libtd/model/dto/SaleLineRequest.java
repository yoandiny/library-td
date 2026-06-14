package mg.yoan.libtd.model.dto;

import lombok.Data;

@Data
public class SaleLineRequest {
    private String bookEditionId;
    private Integer quantity;
}