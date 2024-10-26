package com.bank.antifraud.service;

import com.bank.antifraud.dto.CardTransfersDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Entity;

import java.util.List;

public interface SuspiciousCardTransfersService {

    @Transactional
    List<CardTransfersDTO> getListSCardTransfers();

    @Transactional
    void saveCard(CardTransfersDTO newCardTransfers);

    @Transactional
    void updateCard(CardTransfersDTO updateTrDTO);

    void delete(Long id);

    CardTransfersDTO getCardTransfer(Long id);
}
