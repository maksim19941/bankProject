package com.bank.antifraud.service;

import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mapper.AccountTransferMapper;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SuspiciousAccountTransfersServiceImp implements SuspiciousAccountTransfersService {

    private final SuspiciousAccountTransfersRepository satRepository;
    private final AccountTransferMapper accountTransferMapper = AccountTransferMapper.INSTANCE;

    @Autowired
    public SuspiciousAccountTransfersServiceImp(SuspiciousAccountTransfersRepository satRepository) {
        this.satRepository = satRepository;
    }

    @Override
    public AccountTransfersDTO getAccountTransfer(Long id) {

        log.info("Getting account transfer by id: {}", id);
        SuspiciousAccountTransfers accountTransferEntity = satRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "AccountTransferServiceImpl.getAccountTransferById"));
        return accountTransferMapper.toDTO(accountTransferEntity);
    }

    @Override
    public List<AccountTransfersDTO> getListSAccountTransfers() {

        log.info("Getting all account transfers");

        List<SuspiciousAccountTransfers> accountTransfersListEntity = satRepository.findAll();

        return accountTransferMapper.toDTOList(accountTransfersListEntity);
    }

    @Override
    @Transactional
    public HttpStatus saveAccount(AccountTransfersDTO newAccountTransfers) {

        log.info("Getting account transfer by id: {}", newAccountTransfers);

        if (newAccountTransfers.getAccount_transfer_id() == null) {
            throw new ValidationException("Account transfer ID must not be null.", "AccountTransferServiceImpl.saveAccount",
                    HttpStatus.BAD_REQUEST);

        }
        if (newAccountTransfers.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException("Suspicious reason must not be null and must contain at least 3 characters",
                    "AccountTransferServiceImpl.saveAccount", HttpStatus.BAD_REQUEST);
        }

        SuspiciousAccountTransfers addAccountTransfers = accountTransferMapper.toEntity(newAccountTransfers);
        SuspiciousAccountTransfers accountSave = satRepository.save(addAccountTransfers);
        accountTransferMapper.toDTO(accountSave);
        return HttpStatus.OK;
    }

    @Override
    @Transactional
    public HttpStatus updateAccount(AccountTransfersDTO updateTrDTO) {

        log.info("Creating account transfer: {}", updateTrDTO);

        if (updateTrDTO.getAccount_transfer_id() == null) {
            throw new ValidationException(
                    "Account transfer ID must not be null.",
                    "AccountTransferServiceImpl.updateAccount",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (updateTrDTO.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException(
                    "Suspicious reason must not be null and must contain at least 3 characters",
                    "AccountTransferServiceImpl.updateAccount",
                    HttpStatus.BAD_REQUEST);
        }
        SuspiciousAccountTransfers accountTransfers = satRepository.findById(updateTrDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("AccountTransfers not found ID: " + updateTrDTO.getId(),
                        "AccountTransferServiceImpl.updateAccount"));

        accountTransferMapper.updateEntityFromDTO(updateTrDTO, accountTransfers);
        SuspiciousAccountTransfers updateAccount = satRepository.save(accountTransfers);
        accountTransferMapper.toDTO(updateAccount);
        return HttpStatus.OK;
    }

    @Override
    public void deleteAccount(Long id) {

        log.info("Deleting account transfer: {}", id);
        if (!satRepository.existsById(id)) {
            throw new EntityNotFoundException("An object with this ID was not found for deletion, ID: " + id,
                    "PhoneTransferServiceImpl.deletePhoneTransfer");
        }
        satRepository.deleteById(id);
    }
}
