package com.bank.antifraud.service;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SuspiciousPhoneTransfersService {

    List<PhoneTransfersDTO> getListPhoneTransfers();

    @Transactional
    void savePhone(PhoneTransfersDTO newPhoneTransfers);

    @Transactional
    void updatePhone(PhoneTransfersDTO updateTrDTO);

    void delete(Long id);

    PhoneTransfersDTO getPhoneTransfer(Long id);
}
