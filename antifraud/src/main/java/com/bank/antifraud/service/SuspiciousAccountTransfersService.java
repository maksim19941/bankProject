package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransfersDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuspiciousAccountTransfersService {
    @Transactional
    List<AccountTransfersDTO> getListSAccountTransfers();

    @Transactional
    void saveAccount(AccountTransfersDTO newAccountTransfers);

    @Transactional
    void updateAccount(AccountTransfersDTO updateTrDTO);

    void deleteAccount(Long id);

    AccountTransfersDTO getAccountTransfer(Long id);
}
