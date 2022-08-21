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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void addQRCodeTestFail(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(2l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> qrCodeService.addQRCode(actual));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void updateQRCodeTest(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.findByQRCodeId(actual.getQRCodeId())).thenReturn(expect);
        Mockito.when(qrCodeRepository.save(actual)).thenReturn(expect);
        QRCode result = qrCodeService.updateQRCode(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateQRCodeTestFail(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.findByQRCodeId(actual.getQRCodeId())).thenReturn(expect);
        Mockito.when(qrCodeRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> qrCodeService.updateQRCode(actual));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void deleteQRCodeTest(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.findByQRCodeId(actual.getQRCodeId())).thenReturn(expect);
        qrCodeRepository.delete(actual);
        Mockito.verify(qrCodeRepository,Mockito.timeout(1)).delete(actual);
        boolean result = qrCodeService.deleteQRCode(actual.getQRCodeId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void getAllQRCodesTest(){
        List<QRCode> expect = new ArrayList<>();
        expect.add(new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1"));
        List<QRCode> actual = new ArrayList<>();
        actual.add(new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2"));

        Mockito.when(qrCodeRepository.findAll()).thenReturn(expect);
        List<QRCode> results = qrCodeService.getAllQRCodes();
        Assert.assertEquals(results,expect);
    }

    @Test
    public void getAllQRCodesTestFail(){
        List<QRCode> expect = new ArrayList<>();
        expect.add(new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1"));
        List<QRCode> actual = new ArrayList<>();
        actual.add(new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2"));

        Mockito.when(qrCodeRepository.findAll()).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> qrCodeService.getAllQRCodes());
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getQRCodeByIdTest(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.findByQRCodeId(actual.getQRCodeId())).thenReturn(expect);
        QRCode result = qrCodeService.getQRCodeById(actual.getQRCodeId());
        Assert.assertEquals(result,expect);
    }

    @Test
    public void getQRCodeByIdTestFail(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.findByQRCodeId(actual.getQRCodeId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> qrCodeService.getQRCodeById(actual.getQRCodeId()));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void checkQRCodeExistTest(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.checkQRCodeExist(actual.getQRCodeLink())).thenReturn(expect);
        boolean result = qrCodeService.checkQRCodeExist(actual.getQRCodeLink());

        Assert.assertEquals(result,true);
    }

    @Test
    public void checkQRCodeExistTestFail(){
        QRCode expect = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/1");
        QRCode actual = new QRCode(1l,"https://fos-web.herokuapp.com/khach-hang/table/2");

        Mockito.when(qrCodeRepository.checkQRCodeExist(actual.getQRCodeLink())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> qrCodeService.checkQRCodeExist(actual.getQRCodeLink()));

        Assert.assertEquals("",result.getMessage());
    }
}
