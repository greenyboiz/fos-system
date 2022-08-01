package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.repo.QRCodeRepository;
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
public class QRCodeServiceTest {
    @InjectMocks
    private QRCodeService qrCodeService;

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Test
    public void addQRCodeTest(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(2l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.save(actual)).thenReturn(expect);
        QRCode result = qrCodeService.addQRCode(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateQRCodeTest(){

    }
}
