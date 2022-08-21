package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.repo.QRCodeRepository;
import fpt.edu.capstone.repo.TableRepository;
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
public class TablesServiceTest {

    @InjectMocks
    private TableService tableService;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Test
    public void getAllTablesTest(){
        List<Tables> expect = new ArrayList<>();
        expect.add(new Tables(1l,5,true,true,null));

        List<Tables> actual = new ArrayList<>();
        actual.add(new Tables(1l,5,true,true,null));
        Mockito.when(tableRepository.findAll()).thenReturn(actual);
        List<Tables> results = tableService.getAllTables();
        Assert.assertEquals(results,actual);
    }

    @Test
    public void getAllTablesTestFail(){
        List<Tables> expect = new ArrayList<>();
        expect.add(new Tables(1l,5,true,true,null));
        List<Tables> actual = new ArrayList<>();
        actual.add(new Tables(1l,5,true,true,null));
        Mockito.when(tableRepository.findAll()).thenThrow(new NullPointerException(""));
        NullPointerException results = assertThrows(NullPointerException.class, () -> tableService.getAllTables());
        Assert.assertEquals("",results.getMessage());
    }

    @Test
    public void addTableTest(){
        Tables newTable = new Tables(1l,5,true,true,null);
        Tables expectTable = new Tables(1l,5,true,true,null);
        Mockito.when(tableRepository.save(newTable)).thenReturn(expectTable);
        Tables result = tableService.addTable(newTable);
        Assert.assertEquals(result,expectTable);
    }

    @Test
    public void addTableTestFail(){
        Tables newTable = new Tables(1l,5,true,true,null);
        Tables expectTable = new Tables(1l,5,true,true,null);
        Mockito.when(tableRepository.save(newTable)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> tableService.addTable(newTable));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void updateTableTest(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");

        Tables oldTable = new Tables(1l,4,true,true,qrCodeExpect);
        Tables tableUpdate = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(qrCodeRepository.findByQRCodeLink(tableUpdate.getQrCode().getQRCodeLink())).thenReturn(qrCodeUpdate);

        Mockito.when(tableRepository.findTableById(tableUpdate.getTableId())).thenReturn(oldTable);
        Mockito.when(tableRepository.save(oldTable)).thenReturn(tableUpdate);
        Tables result = tableService.updateTable(oldTable);

        Assert.assertEquals(result,tableUpdate);
    }

    @Test
    public void updateTableTestFail(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");

        Tables oldTable = new Tables(1l,4,true,true,qrCodeExpect);
        Tables tableUpdate = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(qrCodeRepository.findByQRCodeLink(tableUpdate.getQrCode().getQRCodeLink())).thenReturn(qrCodeUpdate);

        Mockito.when(tableRepository.findTableById(tableUpdate.getTableId())).thenReturn(oldTable);
        Mockito.when(tableRepository.save(oldTable)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> tableService.updateTable(oldTable));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void deleteTableByIdTest(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables table = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);
        Mockito.when(tableRepository.getById(table.getTableId())).thenReturn(actual);


        Boolean result = tableService.deleteTable(actual.getTableId());

        Assert.assertEquals(result,true);

    }

    @Test
    public void checkTableExistTest(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables table = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(tableRepository.findTableById(actual.getTableId())).thenReturn(table);
        boolean result = tableService.checkTableExist(actual.getTableId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void checkTableExistTestFail(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables table = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(tableRepository.findTableById(actual.getTableId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> tableService.checkTableExist(actual.getTableId()));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getQRCodeIdByTableIdTest(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables expect = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(tableRepository.findQRCodeIdByTableId(actual.getTableId())).thenReturn(expect.getTableId());
        Long result = tableService.getQRCodeIdByTableId(actual.getTableId());
        Assert.assertEquals(result,expect.getTableId());
    }

    @Test
    public void getQRCodeIdByTableIdTestFail(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables expect = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);
        Mockito.when(tableRepository.findQRCodeIdByTableId(actual.getTableId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> tableService.getQRCodeIdByTableId(actual.getTableId()));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void checkTableIsEmptyTest(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables expect = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);

        Mockito.when(tableRepository.checkTableIsEmpty(actual.getQrCode().getQRCodeId())).thenReturn(true);
        boolean result = tableService.checkTableIsEmpty(actual.getQrCode().getQRCodeId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void checkTableIsEmptyTestFail(){
        QRCode qrCodeExpect = new QRCode(1l, "link1");
        QRCode qrCodeUpdate = new QRCode(2l, "link2");
        Tables expect = new Tables(1l,4,true,true,qrCodeExpect);
        Tables actual = new Tables(1l,4,true,true,qrCodeUpdate);
        Mockito.when(tableRepository.checkTableIsEmpty(actual.getQrCode().getQRCodeId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> tableService.checkTableIsEmpty(actual.getQrCode().getQRCodeId()));
        Assert.assertEquals("",result.getMessage());
    }
}
