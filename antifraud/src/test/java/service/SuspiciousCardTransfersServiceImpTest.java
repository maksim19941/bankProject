package service;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mapper.CardTransferMapper;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import com.bank.antifraud.service.SuspiciousCardTransfersServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SuspiciousCardTransfersServiceImpTest {

    @Mock
    private SuspiciousCardTransfersRepository sctRepository;
    @Mock
    private CardTransferMapper cardTransferMapper = CardTransferMapper.INSTANCE;
    @Mock
    private CardTransferMapper mapper;

    private CardTransfersDTO cardTransferDTO;
    private SuspiciousCardTransfers cardTransfers;

    @InjectMocks
    private SuspiciousCardTransfersServiceImp service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cardTransferDTO = CardTransfersDTO.builder()
                .id(1L)                          // Set ID
                .card_transfer_id(12345L)         // Set Card transfer ID
                .isBlocked(false)                // Set Is blocked
                .isSuspicious(true)              // Set Is suspicious
                .blockedReason("activity")       // Set Blocked reason
                .suspiciousReason("Suspicious activity detected") // Set Suspicious reason
                .build();
    }

    private SuspiciousCardTransfers cardTransfersInit(Long id) {

        cardTransfers = SuspiciousCardTransfers.builder()
                .id(1L)                          // Set ID
                .card_transfer_id(12345L)         // Set Card transfer ID
                .isBlocked(false)                // Set Is blocked
                .isSuspicious(true)              // Set Is suspicious
                .blockedReason("activity")       // Set Blocked reason
                .suspiciousReason("Suspicious activity detected") // Set Suspicious reason
                .build();
        return cardTransfers;
    }


    @Test
    void getListSCardTransfers_ReturnsList() {

        List<SuspiciousCardTransfers> transfersList = List.of(
                cardTransfersInit(1L), cardTransfersInit(2L), cardTransfersInit(3L));
        List<CardTransfersDTO> transfersListDTO = mapper.toDTOList(transfersList);

        Mockito.when(sctRepository.findAll()).thenReturn(transfersList);

        assertEquals(transfersList, transfersListDTO);

        // Act
        var result = service.getListSCardTransfers();


        assertEquals(3, result.size());
        verify(sctRepository, times(1)).findAll();
    }

    @Test
    void saveCard_CardTransferIDNull_ThrowsValidationException() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(null);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.saveCard(dto));
        assertEquals("Card transfer ID must not be null.", exception.getMessage());
    }

    @Test
    void saveCard_SuspiciousReasonTooShort_ThrowsValidationException() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(1L);
        dto.setSuspiciousReason("ab");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.saveCard(dto));
        assertEquals("Suspicious reason must not be null and must contain at least 3 characters", exception.getMessage());
    }

    @Test
    void saveCard_ValidData_SavesCard() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(1L);
        dto.setSuspiciousReason("Valid reason");

        SuspiciousCardTransfers entity = cardTransferMapper.toEntity(dto);
        when(sctRepository.save(any(SuspiciousCardTransfers.class))).thenReturn(entity);

        // Act
        HttpStatus result = service.saveCard(dto);

        // Assert
        assertEquals(HttpStatus.OK, result);
        verify(sctRepository, times(1)).save(any(SuspiciousCardTransfers.class));
    }

    @Test
    void updateCard_CardTransferIDNull_ThrowsValidationException() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(null);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.updateCard(dto));
        assertEquals("Card transfer ID must not be null.", exception.getMessage());
    }

    @Test
    void updateCard_SuspiciousReasonTooShort_ThrowsValidationException() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(1L);
        dto.setSuspiciousReason("ab");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.updateCard(dto));
        assertEquals("Suspicious reason must not be null and must contain at least 3 characters", exception.getMessage());
    }

    @Test
    void updateCard_CardNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setCard_transfer_id(1L);
        dto.setSuspiciousReason("Valid reason");
        when(sctRepository.findById(dto.getCard_transfer_id())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.updateCard(dto));
        assertEquals("CardTransfers not found ID: null", exception.getMessage());
    }

    @Test
    void updateCard_ValidData_UpdatesCard() {
        // Arrange
        CardTransfersDTO dto = new CardTransfersDTO();
        dto.setId(1L);
        dto.setCard_transfer_id(1L);
        dto.setSuspiciousReason("Valid reason");

        SuspiciousCardTransfers existingEntity = new SuspiciousCardTransfers();
        when(sctRepository.findById(dto.getCard_transfer_id())).thenReturn(Optional.of(existingEntity));
        when(sctRepository.save(existingEntity)).thenReturn(existingEntity);

        // Act
        HttpStatus result = service.updateCard(dto);

        // Assert
        assertEquals(HttpStatus.OK, result);
        verify(sctRepository, times(1)).save(existingEntity);
    }

    @Test
    void delete_CardNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        when(sctRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.delete(id));
        assertEquals("An object with this ID was not found for deletion, ID: 1", exception.getMessage());
    }

    @Test
    void delete_ValidId_DeletesCard() {
        // Arrange
        Long id = 1L;
        when(sctRepository.existsById(id)).thenReturn(true);

        // Act
        service.delete(id);

        // Assert
        verify(sctRepository, times(1)).deleteById(id);
    }

    @Test
    void getCardTransfer_CardNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        when(sctRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.getCardTransfer(id));
        assertEquals("An object with this ID was not found, ID: 1", exception.getMessage());
    }

    @Test
    void getCardTransfer_ValidId_ReturnsCardTransfer() {
        // Arrange
        Long id = 1L;
        SuspiciousCardTransfers entity = new SuspiciousCardTransfers();
        when(sctRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        var result = service.getCardTransfer(id);

        // Assert
        assertNotNull(result);
        verify(sctRepository, times(1)).findById(id);
    }
}
