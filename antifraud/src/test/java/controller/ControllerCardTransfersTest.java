package controller;

import com.bank.antifraud.AntifraudApplication;
import com.bank.antifraud.controller.ControllerCardTransfers;
import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.service.SuspiciousCardTransfersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerCardTransfers.class)
@ContextConfiguration(classes = AntifraudApplication.class)
public class ControllerCardTransfersTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SuspiciousCardTransfersService cardService;
    private CardTransfersDTO cardTransfer;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        cardTransfer = CardTransfersDTO.builder()
                .id(1L)                          // Set ID
                .card_transfer_id(12345L)         // Set Card transfer ID
                .isBlocked(false)                // Set Is blocked
                .isSuspicious(true)              // Set Is suspicious
                .blockedReason("activity")       // Set Blocked reason
                .suspiciousReason("Suspicious activity detected") // Set Suspicious reason
                .build();
    }

    @Test
    void getCardTest() throws Exception {
        when(cardService.getCardTransfer(anyLong())).thenReturn(cardTransfer);

        mockMvc.perform(get("/card_transfers/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllCardTest() throws Exception {
        when(cardService.getListSCardTransfers()).thenReturn(Arrays.asList(cardTransfer));

        mockMvc.perform(get("/card_transfers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deleteCardTest() throws Exception {
        doNothing().when(cardService).delete(anyLong());

        mockMvc.perform(delete("/card_transfers/{id}", 1))
                .andExpect(status().isOk());
        Mockito.verify(cardService).delete(eq(1L));
        verify(cardService, times(1)).delete(1L);
    }

    @Test
    void saveCardTest() throws Exception {
        mockMvc.perform(post("/card_transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransfer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Объект сохранен"));

        verify(cardService, times(1)).saveCard(any(CardTransfersDTO.class));
    }

    @Test
    void updateCardTest() throws Exception {

        mockMvc.perform(put("/card_transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransfer)))
                .andExpect(status().isOk())
                .andExpect(content().string("Объект обновлён"));
    }

    @Test
    void getCardTestException() throws Exception {
        when(cardService.getCardTransfer(anyLong())).thenReturn(null);

        mockMvc.perform(get("/card_transfers/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCardExceptionIsNotFound() throws Exception {

        Long id = 999L;
        doThrow(new EntityNotFoundException("An object with this ID was not found for deletion, ID: " + id,
                "cardTransferServiceImpl.deleteCardTransfer")).when(cardService).delete(anyLong());

        mockMvc.perform(delete("/card_transfers/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("An object with this ID was not found for deletion, ID: " + id)); // Предполагается, что контроллер возвращает это сообщение
    }

    @Test
    void saveCardExceptionTestValidationException() throws Exception {

        doThrow(new ValidationException("Card transfer ID must not be null.",
                "CardTransferServiceImpl.saveCard", HttpStatus.BAD_REQUEST))
                .when(cardService)
                .saveCard(any(CardTransfersDTO.class));

        mockMvc.perform(post("/card_transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardTransfer)))
                .andExpect(status().isBadRequest());
    }
}
