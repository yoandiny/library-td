package mg.yoan.libtd.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.*;
import mg.yoan.libtd.model.dto.SaleLineRequest;
import mg.yoan.libtd.model.dto.SaleRequest;
import mg.yoan.libtd.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

  private final SaleRepository saleRepository;
  private final SaleLineRepository saleLineRepository;
  private final CustomerRepository customerRepository;
  private final BookEditionRepository bookEditionRepository;

  public SaleService(
      SaleRepository saleRepository,
      SaleLineRepository saleLineRepository,
      CustomerRepository customerRepository,
      BookEditionRepository bookEditionRepository) {
    this.saleRepository = saleRepository;
    this.saleLineRepository = saleLineRepository;
    this.customerRepository = customerRepository;
    this.bookEditionRepository = bookEditionRepository;
  }

  @Transactional
  public Sale create(SaleRequest request) {
    Customer customer =
        customerRepository
            .findById(request.getCustomerId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Customer with id " + request.getCustomerId() + " not found"));

    Sale sale =
        Sale.builder()
            .customer(customer)
            .saleDate(LocalDateTime.now())
            .status(SaleStatus.IN_PROGRESS)
            .totalAmount(BigDecimal.ZERO)
            .build();

    Sale savedSale = saleRepository.save(sale);

    List<SaleLine> lines =
        request.getLines().stream().map(l -> buildSaleLine(savedSale, l)).toList();

    saleLineRepository.saveAll(lines);

    BigDecimal total =
        lines.stream()
            .map(l -> l.getUnitPrice().multiply(BigDecimal.valueOf(l.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    savedSale.setTotalAmount(total);
    savedSale.setSaleLines(lines);

    return saleRepository.save(savedSale);
  }

  public List<Sale> getAll() {
    return saleRepository.findAll();
  }

  public Sale getById(UUID id) {
    return saleRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Sale with id " + id + " not found"));
  }

  public List<Sale> getByCustomer(UUID customerId) {
    if (!customerRepository.existsById(customerId)) {
      throw new NotFoundException("Customer with id " + customerId + " not found");
    }
    return saleRepository.findAllByCustomerId(customerId);
  }

  @Transactional
  public Sale validate(UUID id) {
    Sale sale = getById(id);
    sale.validate();
    return saleRepository.save(sale);
  }

  @Transactional
  public Sale cancel(UUID id) {
    Sale sale = getById(id);
    sale.cancel();
    return saleRepository.save(sale);
  }

  private SaleLine buildSaleLine(Sale sale, SaleLineRequest lineRequest) {
    BookEdition bookEdition =
        bookEditionRepository
            .findById(lineRequest.getBookEditionId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "BookEdition with id " + lineRequest.getBookEditionId() + " not found"));

    if (bookEdition.getPrice() == null) {
      throw new IllegalArgumentException(
          "BookEdition with id " + bookEdition.getId() + " has no price set");
    }

    return SaleLine.builder()
        .sale(sale)
        .bookEdition(bookEdition)
        .unitPrice(BigDecimal.valueOf(bookEdition.getPrice()))
        .quantity(lineRequest.getQuantity())
        .build();
  }
}
