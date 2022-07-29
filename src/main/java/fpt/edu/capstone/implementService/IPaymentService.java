package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Payment;

import java.math.BigDecimal;

public interface IPaymentService {
    public BigDecimal getTotalAmountByOrder(Long orderId);

    public Payment getTotalMoneyOrder(Long orderId);

    public Payment savePayment(Payment payment);
}
