package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.PaymentType;
import fpt.edu.capstone.repo.PaymentTypeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentTypeServiceTest {
    @InjectMocks
    private PaymentTypeService paymentTypeService;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    @Test
    public void getPaymentTypeNameByPaymentTypeIdTest(){
        PaymentType expect = new PaymentType(1l,"banking");
        PaymentType actual = new PaymentType(1l, "cash");
        Mockito.when(paymentTypeRepository.findPaymentTypeNameByPaymentTypeId(actual.getPaymentTypeName())).thenReturn(expect.getPaymentTypeId());
        Long result = paymentTypeService.getPaymentTypeNameByPaymentTypeId(actual.getPaymentTypeName());
        Assert.assertEquals(result,expect.getPaymentTypeId());
    }

    @Test
    public void getPaymentTypeByPaymentTypeIdTest(){
        PaymentType expect = new PaymentType(1l,"banking");
        PaymentType actual = new PaymentType(1l, "cash");
        Mockito.when(paymentTypeRepository.findPaymentTypeByPaymentTypeId(actual.getPaymentTypeId())).thenReturn(expect);
        PaymentType result = paymentTypeService.getPaymentTypeByPaymentTypeId(actual.getPaymentTypeId());
        Assert.assertEquals(result,expect);
    }
}
