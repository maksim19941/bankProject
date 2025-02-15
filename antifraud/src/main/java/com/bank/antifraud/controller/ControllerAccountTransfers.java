package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.service.SuspiciousAccountTransfersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/account_transfers")
public class ControllerAccountTransfers {

    private final SuspiciousAccountTransfersService sats;

    @GetMapping("/{id}")
    @Operation(summary = "Get account transfer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of account transfer")
    @ApiResponse(responseCode = "404", description = "Account transfer not found")
    public ResponseEntity<AccountTransfersDTO> getSAT(@PathVariable Long id) {

        log.info("Retrieving account transfer with ID: {}", id);

        AccountTransfersDTO transfer = sats.getAccountTransfer(id);

        if (transfer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(transfer);
    }

    @GetMapping()
    @Operation(summary = "Get all account transfers")
    @ApiResponse(responseCode = "200", description = "List of all account transfers")
    public ResponseEntity<List<AccountTransfersDTO>> getAllSAT() {

        log.info("Retrieving all account transfers");

        List<AccountTransfersDTO> transfers = sats.getListSAccountTransfers();

        return ResponseEntity.ok(transfers);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account transfer by ID")
    @ApiResponse(responseCode = "200", description = "Account transfer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Account transfer not found")
    public ResponseEntity<String> deleteSAT(@PathVariable Long id) {

        log.info("Deleting account transfer with ID: {}", id);

        sats.deleteAccount(id);

        return ResponseEntity.status(HttpStatus.OK).body("Объект удалён");
    }

    @PostMapping()
    @Operation(summary = "Save new account transfer")
    @ApiResponse(responseCode = "201", description = "New account transfer saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<String> saveSAT(@RequestBody AccountTransfersDTO transfersDTO) {

        log.info("Saving new account transfer: {}", transfersDTO);

        try {
            sats.saveAccount(transfersDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Объект сохранен");
        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @PutMapping()
    @Operation(summary = "Update existing account transfer")
    @ApiResponse(responseCode = "200", description = "Account transfer updated successfully")
    @ApiResponse(responseCode = "400", description = "Account transfer not found")
    public ResponseEntity<String> updateSAT(@RequestBody AccountTransfersDTO transfersDTO) {

        log.info("Updating account transfer with ID: {}, Data: {}", transfersDTO.getId(), transfersDTO);

        try {
            sats.updateAccount(transfersDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Объект обновлён");
        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
