package mg.yoan.libtd.service;

import java.util.List;
import java.util.UUID;

import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.Format;
import mg.yoan.libtd.model.dto.FormatRequest;
import mg.yoan.libtd.repository.FormatRepository;
import org.springframework.stereotype.Service;

@Service
public class FormatService {

  private final FormatRepository formatRepository;

  public FormatService(FormatRepository formatRepository) {
    this.formatRepository = formatRepository;
  }

  public Format create(FormatRequest request) {
    Format format = Format.builder().formatLabel(request.getFormatLabel()).build();
    return formatRepository.save(format);
  }

  public List<Format> getAll() {
    return formatRepository.findAll();
  }

  public Format getById(UUID id) {
    return formatRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Format with id " + id + " not found"));
  }

  public Format update(UUID id, FormatRequest request) {
    Format format = getById(id);
    format.setFormatLabel(request.getFormatLabel());
    return formatRepository.save(format);
  }

  public void delete(UUID id) {
    if (!formatRepository.existsById(id)) {
      throw new NotFoundException("Format with id " + id + " not found");
    }
    formatRepository.deleteById(id);
  }
}
