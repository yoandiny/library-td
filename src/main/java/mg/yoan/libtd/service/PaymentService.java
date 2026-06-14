package mg.yoan.libtd.service;

import java.util.List;
import mg.yoan.libtd.exception.NotFoundException;
import mg.yoan.libtd.model.*;
import mg.yoan.libtd.model.dto.PaymentRequest;
import mg.yoan.libtd.repository.PaymentRepository;
import mg.yoan.libtd.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SaleRepository saleRepository;

    public PaymentService(PaymentRepository paymentRepository, SaleRepository saleRepository) {
        this.paymentRepository = paymentRepository;
        this.saleRepository = saleRepository;
    }

    @Transactional
    public Payment create(PaymentRequest request) {
        Sale sale =
                saleRepository
                        .findById(request.getSaleId())
                        .orElseThrow(
                                () ->
                                        new NotFoundException("Sale with id " + request.getSaleId() + " not found"));

        if (!SaleStatus.VALIDATED.equals(sale.getStatus())) {
            throw new IllegalStateException("Payment can only be created for VALIDATED sales");
        }

        paymentRepository
                .findBySaleId(sale.getId())
                .ifPresent(
                        p -> {
                            throw new IllegalStateException(
                                    "A payment already exists for sale " + sale.getId());
                        });

        Payment payment =
                Payment.builder()
                        .sale(sale)
                        .amount(sale.getTotalAmount())
                        .method(request.getMethod())
                        .status(PaymentStatus.PENDING)
                        .build();

        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment process(String paymentId) {
        Payment payment = getById(paymentId);
        payment.process();
        return paymentRepository.save(payment);
    }

    public Payment getById(String id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Payment with id " + id + " not found"));
    }

    public Payment getBySaleId(String saleId) {
        return paymentRepository
                .findBySaleId(saleId)
                .orElseThrow(() -> new NotFoundException("No payment found for sale " + saleId));
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Transactional
    public Payment failPayment(String paymentId) {
        Payment payment = getById(paymentId);
        if (!PaymentStatus.PENDING.equals(payment.getStatus())) {
            throw new IllegalStateException("Only PENDING payments can be marked as failed");
        }
        payment.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }
}