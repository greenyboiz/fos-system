package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Payment;
import fpt.edu.capstone.entities.PaymentType;
import fpt.edu.capstone.implementService.IPaymentTypeService;
import fpt.edu.capstone.repo.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService implements IPaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Override
    public Long getPaymentTypeNameByPaymentTypeId(String paymentType) {
        Long paymentTypeId = paymentTypeRepository.findPaymentTypeNameByPaymentTypeId(paymentType);
        return paymentTypeId;
    }

    @Override
    public PaymentType getPaymentTypeByPaymentTypeId(Long paymentTypeId) {
        PaymentType paymentType = paymentTypeRepository.findPaymentTypeByPaymentTypeId(paymentTypeId);
        return paymentType;
    }
}
