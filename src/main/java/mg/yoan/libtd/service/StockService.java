package mg.yoan.libtd.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import mg.yoan.libtd.repository.ArrivalLineRepository;
import mg.yoan.libtd.repository.SaleLineRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

  private final ArrivalLineRepository arrivalLineRepository;
  private final SaleLineRepository saleLineRepository;

  public int getStock(UUID bookEditionId) {
    int arrived = arrivalLineRepository.sumQuantityByBookEditionId(bookEditionId);
    int sold = saleLineRepository.sumQuantityByBookEditionId(bookEditionId);
    return arrived - sold;
  }
}
