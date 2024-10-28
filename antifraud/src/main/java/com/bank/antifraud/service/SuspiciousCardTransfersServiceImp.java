package com.bank.antifraud.service;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.exception.EntityNotFoundException;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mapper.CardTransferMapper;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SuspiciousCardTransfersServiceImp implements SuspiciousCardTransfersService {

    private final SuspiciousCardTransfersRepository sctRepository;
    private final CardTransferMapper cardTransferMapper = CardTransferMapper.INSTANCE;

    @Autowired
    public SuspiciousCardTransfersServiceImp(SuspiciousCardTransfersRepository sctRepository) {
        this.sctRepository = sctRepository;
    }

    @Override
    public List<CardTransfersDTO> getListSCardTransfers() {

        log.info("Getting all card transfers");

        List<SuspiciousCardTransfers> cardTransfersListEntity = sctRepository.findAll();

        return cardTransferMapper.toDTOList(cardTransfersListEntity);
    }

    @Override
    @Transactional
    public HttpStatus saveCard(CardTransfersDTO newCardTransfers) {

        log.info("Getting card transfer by id: {}", newCardTransfers);

        if (newCardTransfers.getCard_transfer_id() == null) {
            throw new ValidationException("Card transfer ID must not be null.", "CardTransferServiceImpl.saveCard",
                    HttpStatus.BAD_REQUEST);

        }
        if (newCardTransfers.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException("Suspicious reason must not be null and must contain at least 3 characters",
                    "cardTransferServiceImpl.saveCard", HttpStatus.BAD_REQUEST);
        } else {

            SuspiciousCardTransfers cardTransfers = cardTransferMapper.toEntity(newCardTransfers);
            SuspiciousCardTransfers cardSave = sctRepository.save(cardTransfers);
            cardTransferMapper.toDTO(cardSave);
            return HttpStatus.OK;
        }
    }

    @Override
    @Transactional
    public HttpStatus updateCard(CardTransfersDTO updateTrDTO) {
        log.info("Updating card transfer: {}", updateTrDTO);

        if (updateTrDTO.getCard_transfer_id() == null) {
            throw new ValidationException(
                    "Card transfer ID must not be null.",
                    "CardTransferServiceImpl.updateCard",
                    HttpStatus.BAD_REQUEST
            );
        }
        if (updateTrDTO.getSuspiciousReason().trim().length() < 3) {
            throw new ValidationException(
                    "Suspicious reason must not be null and must contain at least 3 characters",
                    "CardTransferServiceImpl.updateCard",
                    HttpStatus.BAD_REQUEST);
        }
        SuspiciousCardTransfers cardTransfers = sctRepository.findById(updateTrDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "CardTransfers not found ID: " + updateTrDTO.getId(),
                        "CardTransferServiceImpl.updateCard"
                ));

        cardTransferMapper.updateEntityFromDTO(updateTrDTO, cardTransfers);
        SuspiciousCardTransfers updatedCard = sctRepository.save(cardTransfers);
        cardTransferMapper.toDTO(updatedCard);
        return HttpStatus.OK;
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting card transfer: {}", id);
        if (!sctRepository.existsById(id)) {
            throw new EntityNotFoundException("An object with this ID was not found for deletion, ID: " + id,
                    "cardTransferServiceImpl.deleteCardTransfer");
        }
        sctRepository.deleteById(id);
    }

    @Override
    public CardTransfersDTO getCardTransfer(Long id) {

        log.info("Getting card transfer by id: {}", id);
        SuspiciousCardTransfers cardTransferEntity = sctRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("An object with this ID was not found, ID: " + id,
                                "cardTransferServiceImpl.getCardTransferById"));
        return cardTransferMapper.toDTO(cardTransferEntity);
    }
}

