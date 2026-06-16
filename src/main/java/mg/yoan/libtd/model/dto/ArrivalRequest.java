package mg.yoan.libtd.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ArrivalRequest {
  private LocalDateTime arrivedAt;
  private List<ArrivalLineRequest> lines;
}
