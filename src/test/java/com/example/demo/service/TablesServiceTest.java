package com.example.demo.service;

import com.example.demo.entities.QRCode;
import com.example.demo.entities.Tables;
import com.example.demo.repo.QRCodeRepository;
import com.example.demo.repo.TableRepository;
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
        expect.add(new Tables(1l,"5","1",null));

        List<Tables> actual = new ArrayList<>();
        actual.add(new Tables(1l,"5","1",null));
        Mockito.when(tableRepository.findAll()).thenReturn(actual);
        List<Tables> results = tableService.getAllTables();
        Assert.assertEquals(results,actual);
    }

    @Test
    public void addTableTest(){
        Tables newTable = new Tables(1l,"5","1",null);
        Tables expectTable = new Tables(1l,"5","1",null);


        Mockito.when(tableRepository.save(newTable)).thenReturn(expectTable);
        Tables result = tableService.addTable(newTable);
        Assert.assertEquals(result,expectTable);
    }

    @Test
    public void updateTableTest(){
        QRCode qrCodeExpect = new QRCode(1l, "abc","link1");
        QRCode qrCodeUpdate = new QRCode(2l, "abcd","link2");

        Tables oldTable = new Tables(1l,"4","1",qrCodeExpect);
        Tables tableUpdate = new Tables(1l,"4","1",qrCodeUpdate);

        Mockito.when(qrCodeRepository.findByQRCodeId(tableUpdate.getQrCode().getQRCodeId())).thenReturn(qrCodeUpdate);

        Mockito.when(tableRepository.getById(tableUpdate.getTableId())).thenReturn(oldTable);
        Mockito.when(tableRepository.save(oldTable)).thenReturn(tableUpdate);
        Tables result = tableService.updateTable(tableUpdate);

        Assert.assertEquals(result,tableUpdate);
    }

    @Test
    public void deleteTableByIdTest(){
        QRCode qrCodeExpect = new QRCode(1l, "abc","link1");
        QRCode qrCodeUpdate = new QRCode(2l, "abcd","link2");
        Tables table = new Tables(1l,"4","1",qrCodeExpect);
        Tables actual = new Tables(1l,"4","1",qrCodeUpdate);
        Mockito.when(tableRepository.getById(table.getTableId())).thenReturn(actual);


        Boolean result = tableService.deleteTable(actual.getTableId());

        Assert.assertEquals(result,true);

    }
}
