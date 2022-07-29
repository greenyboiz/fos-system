package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.PaymentType;

public interface IPaymentTypeService {
    Long getPaymentTypeNameByPaymentTypeId(String paymentType);

    PaymentType getPaymentTypeByPaymentTypeId(Long paymentTypeId);
}
