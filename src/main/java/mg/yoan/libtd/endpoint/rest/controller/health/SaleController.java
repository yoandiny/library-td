package mg.yoan.libtd.endpoint.rest.controller.health;

import java.util.List;
import java.util.UUID;
import mg.yoan.libtd.model.Sale;
import mg.yoan.libtd.model.dto.SaleRequest;
import mg.yoan.libtd.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SaleController {

  private final SaleService saleService;

  public SaleController(SaleService saleService) {
    this.saleService = saleService;
  }

  @PostMapping
  public ResponseEntity<Sale> createSale(@RequestBody SaleRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<Sale>> getAllSales(@RequestParam(required = false) UUID customerId) {
    if (customerId != null) {
      return ResponseEntity.ok(saleService.getByCustomer(customerId));
    }
    return ResponseEntity.ok(saleService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Sale> getSaleById(@PathVariable UUID id) {
    return ResponseEntity.ok(saleService.getById(id));
  }

  @PutMapping("/{id}/validate")
  public ResponseEntity<Sale> validateSale(@PathVariable UUID id) {
    return ResponseEntity.ok(saleService.validate(id));
  }

  @PutMapping("/{id}/cancel")
  public ResponseEntity<Sale> cancelSale(@PathVariable UUID id) {
    return ResponseEntity.ok(saleService.cancel(id));
  }
}
