package mg.yoan.libtd.model.dto;

import lombok.Data;
import mg.yoan.libtd.model.CopyCondition;
import mg.yoan.libtd.model.CopyStatus;

@Data
public class BookCopyRequest {
    private Long bookId;
    private String barcode;
    private CopyCondition condition;
    private CopyStatus status;
    private String location;
}