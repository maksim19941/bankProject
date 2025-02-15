package com.bank.antifraud.controller;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.service.SuspiciousPhoneTransfersService;
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
@RequestMapping("/phone_transfers")
@AllArgsConstructor
public class ControllerPhoneTransfers {

    private final SuspiciousPhoneTransfersService phoneService;

    @GetMapping("/{id}")
    @Operation(summary = "Get phone transfer by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of phone transfer")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public ResponseEntity getSAT(@PathVariable Long id) {
        PhoneTransfersDTO transfer = phoneService.getPhoneTransfer(id);

        if (transfer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(transfer);
    }

    @GetMapping()
    @Operation(summary = "Get all phone transfers")
    @ApiResponse(responseCode = "200", description = "List of all phone transfers")
    public ResponseEntity<List<PhoneTransfersDTO>>  getAllSAT() {

        log.info("Retrieving all card transfers");

        List<PhoneTransfersDTO> transfers = phoneService.getListPhoneTransfers();
        return ResponseEntity.ok(transfers);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete phone transfer by ID")
    @ApiResponse(responseCode = "200", description = "phone transfer deleted successfully")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public ResponseEntity<String> deleteSAT(@PathVariable Long id) {

        log.info("Deleting phone transfer with ID: {}", id);

        return ResponseEntity.status(HttpStatus.OK).body("Объект удалён");
    }

    @PostMapping()
    @Operation(summary = "Save new phone transfer")
    @ApiResponse(responseCode = "201", description = "New phone transfer saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<String> saveSAT(@RequestBody PhoneTransfersDTO phoneTransfersDTO) {
        log.info("Saving new card transfer: {}", phoneService);

        try {
            phoneService.savePhone(phoneTransfersDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Объект сохранен");
        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @PutMapping()
    @Operation(summary = "Update existing phone transfer")
    @ApiResponse(responseCode = "200", description = "phone transfer updated successfully")
    @ApiResponse(responseCode = "404", description = "phone transfer not found")
    public ResponseEntity<String> updateSAT(@RequestBody PhoneTransfersDTO phoneTransfersDTO) {

        log.info("Updating card transfer with ID: {}, Data: {}", phoneTransfersDTO.getId(), phoneTransfersDTO);

        try {
            phoneService.updatePhone(phoneTransfersDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Объект обновлён");
        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
