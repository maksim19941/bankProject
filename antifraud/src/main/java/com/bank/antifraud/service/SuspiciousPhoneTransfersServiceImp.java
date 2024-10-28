package com.bank.antifraud.service;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mapper.PhoneTransferMapper;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspicionsPhoneTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SuspiciousPhoneTransfersServiceImp implements SuspiciousPhoneTransfersService {

    private final SuspicionsPhoneTransfersRepository sptRepository;
    private final PhoneTransferMapper phoneTransferMapper = PhoneTransferMapper.INSTANCE;

    @Autowired
    public SuspiciousPhoneTransfersServiceImp(SuspicionsPhoneTransfersRepository sptRepository) {
        this.sptRepository = sptRepository;
    }

    @Override
    public List<PhoneTransfersDTO> getListPhoneTransfers() {

        log.info("Getting all phone transfers");

        List<SuspiciousPhoneTransfers> accountTransfersListEntity = sptRepository.findAll();

        return phoneTransferMapper.toDTOList(accountTransfersListEntity);
    }

    @Override
    @Transactional
    public HttpStatus savePhone(PhoneTransfersDTO newPhoneTransfers) {

        log.info("Getting account transfer by id: {}", newPhoneTransfers);

        if (newPhoneTransfers.getPhone_transfer_id() == null) {
            throw new ValidationException("Phone transfer ID must not be null.", "PhoneTransferServiceImpl.savePhone",
                    HttpStatus.BAD_REQUEST);

        }
        if (newPhoneTransfers.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException("Suspicious reason must not be null and must contain at least 3 characters",
                    "PhoneTransferServiceImpl.savePhone", HttpStatus.BAD_REQUEST);
        }

        SuspiciousPhoneTransfers addPhone = phoneTransferMapper.toEntity(newPhoneTransfers);
        SuspiciousPhoneTransfers accountSave = sptRepository.save(addPhone);
        phoneTransferMapper.toDTO(accountSave);
        return HttpStatus.OK;
    }

    @Override
    @Transactional
    public HttpStatus updatePhone(PhoneTransfersDTO updateTrDTO) {

        log.info("Creating phone transfer: {}", updateTrDTO);

        if (updateTrDTO.getPhone_transfer_id() == null) {
            throw new ValidationException(
                    "Phone transfer ID must not be null.",
                    "PhoneTransferServiceImpl.updatePhone",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (updateTrDTO.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException(
                    "Suspicious reason must not be null and must contain at least 3 characters",
                    "PhoneTransferServiceImpl.updatePhone",
                    HttpStatus.BAD_REQUEST);
        }

        SuspiciousPhoneTransfers phoneTransfers = sptRepository.findById(updateTrDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("PhoneTransfers not found ID: " + updateTrDTO.getId(),
                        "PhoneTransferServiceImpl.updatePhone"));

        phoneTransferMapper.updateEntityFromDTO(updateTrDTO, phoneTransfers);
        SuspiciousPhoneTransfers updateAccount = sptRepository.save(phoneTransfers);
        phoneTransferMapper.toDTO(updateAccount);
        return HttpStatus.OK;
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting phone transfer: {}", id);
        if (!sptRepository.existsById(id)) {
            throw new EntityNotFoundException("An object with this ID was not found for deletion, ID: " + id,
                    "PhoneTransferServiceImpl.deletePhoneTransfer");
        }
        sptRepository.deleteById(id);
    }

    @Override
    public PhoneTransfersDTO getPhoneTransfer(Long id) {

        log.info("Getting phone transfer by id: {}", id);
        SuspiciousPhoneTransfers accountTransferEntity = sptRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "PhoneTransferServiceImpl.getPhoneTransferById"));

        return phoneTransferMapper.toDTO(accountTransferEntity);
    }
}
