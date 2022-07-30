package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.repo.QRCodeRepository;
import fpt.edu.capstone.repo.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TableServiceTest {
    @InjectMocks
    private TableService tableService;

    @Mock
    private TableRepository tableRepository;

    @Mock
    private QRCodeRepository qrCodeRepository;

//    @Test
//    public void getAllTablesTest(){
//        List<Tables> expect = new ArrayList<>();
//        Tables tables = Tables.builder()
//                        .numberOfSeats(8)
//        expect.add( Tables.builder())
//    }

}
