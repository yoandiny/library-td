package mg.yoan.libtd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.Arrival;
import mg.yoan.libtd.model.ArrivalLine;
import mg.yoan.libtd.model.BookEdition;
import mg.yoan.libtd.model.dto.ArrivalLineRequest;
import mg.yoan.libtd.model.dto.ArrivalRequest;
import mg.yoan.libtd.repository.ArrivalRepository;
import mg.yoan.libtd.repository.BookEditionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArrivalService {

  private final ArrivalRepository arrivalRepository;
  private final BookEditionRepository bookEditionRepository;

  public ArrivalService(
      ArrivalRepository arrivalRepository, BookEditionRepository bookEditionRepository) {
    this.arrivalRepository = arrivalRepository;
    this.bookEditionRepository = bookEditionRepository;
  }

  @Transactional
  public Arrival create(ArrivalRequest request) {
    if (request.getLines() == null || request.getLines().isEmpty()) {
      throw new IllegalArgumentException("An arrival must contain at least one line");
    }

    Arrival arrival =
        Arrival.builder()
            .arrivedAt(
                request.getArrivedAt() != null ? request.getArrivedAt() : LocalDateTime.now())
            .build();

    List<ArrivalLine> lines = new ArrayList<>();
    for (ArrivalLineRequest lineRequest : request.getLines()) {
      lines.add(buildLine(arrival, lineRequest));
    }
    arrival.setLines(lines);

    return arrivalRepository.save(arrival);
  }

  public List<Arrival> getAll() {
    return arrivalRepository.findAll();
  }

  public Arrival getById(UUID id) {
    return arrivalRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Arrival with id " + id + " not found"));
  }

  public void delete(UUID id) {
    if (!arrivalRepository.existsById(id)) {
      throw new NotFoundException("Arrival with id " + id + " not found");
    }
    arrivalRepository.deleteById(id);
  }

  private ArrivalLine buildLine(Arrival arrival, ArrivalLineRequest lineRequest) {
    if (lineRequest.getQuantity() == null || lineRequest.getQuantity() <= 0) {
      throw new IllegalArgumentException("Arrival line quantity must be greater than 0");
    }

    BookEdition bookEdition =
        bookEditionRepository
            .findById(lineRequest.getBookEditionId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "BookEdition with id " + lineRequest.getBookEditionId() + " not found"));

    return ArrivalLine.builder()
        .arrival(arrival)
        .bookEdition(bookEdition)
        .quantity(lineRequest.getQuantity())
        .build();
  }
}
