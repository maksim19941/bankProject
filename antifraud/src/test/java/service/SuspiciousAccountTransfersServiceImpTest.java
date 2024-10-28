//package service;
//
//import com.bank.antifraud.dto.AccountTransfersDTO;
//import com.bank.antifraud.exception.EntityNotFoundException;
//import com.bank.antifraud.mapper.AccountTransferMapper;
//import com.bank.antifraud.model.SuspiciousAccountTransfers;
//import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
//import com.bank.antifraud.service.SuspiciousAccountTransfersServiceImp;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class SuspiciousAccountTransfersServiceImpTest {
//
//    @Mock
//    private SuspiciousAccountTransfersRepository satRepository;
//
//    @Mock
//    private AccountTransferMapper accountTransferMapper;
//
//    @InjectMocks
//    private SuspiciousAccountTransfersServiceImp service;
//
//    private SuspiciousAccountTransfers suspiciousAccountTransfer;
//    private AccountTransfersDTO accountTransfersDTO;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        initializeTestData();
//    }
//
//    private void initializeTestData() {
//        suspiciousAccountTransfer = new SuspiciousAccountTransfers();
//        suspiciousAccountTransfer.setId(1L);
//        suspiciousAccountTransfer.setBlocked(false);
//        suspiciousAccountTransfer.setSuspicious(false);
//        suspiciousAccountTransfer.setBlockedReason(null);
//        suspiciousAccountTransfer.setSuspiciousReason("Regular transaction");
//
//        accountTransfersDTO = new AccountTransfersDTO();
//        accountTransfersDTO.setId(1L);
//        accountTransfersDTO.setBlocked(false);
//        accountTransfersDTO.setSuspicious(false);
//        accountTransfersDTO.setBlockedReason(null);
//        accountTransfersDTO.setSuspiciousReason("Regular transaction");
//    }
//
//    @Test
//    void testGetAccountTransfer_Success() {
//        when(satRepository.findById(1L)).thenReturn(Optional.of(suspiciousAccountTransfer));
//        when(accountTransferMapper.toDTO(suspiciousAccountTransfer)).thenReturn(accountTransfersDTO);
//
//        AccountTransfersDTO result = service.getAccountTransfer(1L);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        verify(satRepository).findById(1L);
//    }
//
//    @Test
//    void testGetAccountTransfer_NotFound() {
//        when(satRepository.findById(1L)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
//            service.getAccountTransfer(1L);
//        });
//
//        assertEquals("An object with this ID was not found, ID: 1", exception.getMessage());
//        verify(satRepository).findById(1L);
//    }
//
//    @Test
//    void testGetListAccountTransfers() {
//        when(satRepository.findAll()).thenReturn(Collections.singletonList(suspiciousAccountTransfer));
//        when(accountTransferMapper.toDTOList(Collections.singletonList(suspiciousAccountTransfer)))
//                .thenReturn(Collections.singletonList(accountTransfersDTO));
//
//        var result = service.getListSAccountTransfers();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(1L, result.get(0).getId());
//        verify(satRepository).findAll();
//    }
//
//    @Test
//    void testSaveAccount() {
//        when(accountTransferMapper.toEntity(accountTransfersDTO)).thenReturn(suspiciousAccountTransfer);
//        when(satRepository.save(suspiciousAccountTransfer)).thenReturn(suspiciousAccountTransfer);
//        when(accountTransferMapper.toDTO(suspiciousAccountTransfer)).thenReturn(accountTransfersDTO);
//
//        AccountTransfersDTO result = service.saveAccount(accountTransfersDTO);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        verify(satRepository).save(suspiciousAccountTransfer);
//    }
//
//    @Test
//    void testUpdateAccount_Success() {
//        when(satRepository.findById(1L)).thenReturn(Optional.of(suspiciousAccountTransfer));
//        when(accountTransferMapper.toDTO(suspiciousAccountTransfer)).thenReturn(accountTransfersDTO);
//
//        AccountTransfersDTO result = service.updateAccount(accountTransfersDTO, 1L);
//
//        assertNotNull(result);
//        verify(accountTransferMapper).updateEntityFromDTO(accountTransfersDTO, suspiciousAccountTransfer);
//        verify(satRepository).save(suspiciousAccountTransfer);
//    }
//
//    @Test
//    void testUpdateAccount_NotFound() {
//        when(satRepository.findById(1L)).thenReturn(Optional.empty());
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
//            service.updateAccount(accountTransfersDTO, 1L);
//        });
//
//        assertEquals("AccountTransfers not found", exception.getMessage());
//    }
//
//    @Test
//    void testDeleteAccount_Success() {
//        doNothing().when(satRepository).deleteById(1L);
//
//        assertDoesNotThrow(() -> service.deleteAccount(1L));
//        verify(satRepository).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteAccount_NotFound() {
//        doThrow(new EntityNotFoundException("Not found", "SuspiciousAccountTransfersServiceImp.delete"))
//                .when(satRepository).deleteById(1L);
//
//        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
//            service.deleteAccount(1L);
//        });
//
//        assertEquals("An object with this ID was not found for deletion, ID: 1", exception.getMessage());
//        verify(satRepository).deleteById(1L);
//    }
//}
