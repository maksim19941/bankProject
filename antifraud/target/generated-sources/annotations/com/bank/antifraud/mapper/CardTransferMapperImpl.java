package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-09T11:25:22+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class CardTransferMapperImpl implements CardTransferMapper {

    @Override
    public SuspiciousCardTransfers toEntity(CardTransfersDTO cardTransferDTO) {
        if ( cardTransferDTO == null ) {
            return null;
        }

        SuspiciousCardTransfers.SuspiciousCardTransfersBuilder suspiciousCardTransfers = SuspiciousCardTransfers.builder();

        suspiciousCardTransfers.cardTransferId( cardTransferDTO.getCardTransferId() );
        suspiciousCardTransfers.blocked( cardTransferDTO.isBlocked() );
        suspiciousCardTransfers.suspicious( cardTransferDTO.isSuspicious() );
        suspiciousCardTransfers.blockedReason( cardTransferDTO.getBlockedReason() );
        suspiciousCardTransfers.suspiciousReason( cardTransferDTO.getSuspiciousReason() );

        return suspiciousCardTransfers.build();
    }

    @Override
    public CardTransfersDTO toDTO(SuspiciousCardTransfers cardTransfer) {
        if ( cardTransfer == null ) {
            return null;
        }

        CardTransfersDTO.CardTransfersDTOBuilder cardTransfersDTO = CardTransfersDTO.builder();

        cardTransfersDTO.blocked( cardTransfer.isBlocked() );
        cardTransfersDTO.suspicious( cardTransfer.isSuspicious() );
        cardTransfersDTO.id( cardTransfer.getId() );
        cardTransfersDTO.cardTransferId( cardTransfer.getCardTransferId() );
        cardTransfersDTO.blockedReason( cardTransfer.getBlockedReason() );
        cardTransfersDTO.suspiciousReason( cardTransfer.getSuspiciousReason() );

        return cardTransfersDTO.build();
    }

    @Override
    public List<CardTransfersDTO> toDTOList(List<SuspiciousCardTransfers> cardTransferEntityList) {
        if ( cardTransferEntityList == null ) {
            return null;
        }

        List<CardTransfersDTO> list = new ArrayList<CardTransfersDTO>( cardTransferEntityList.size() );
        for ( SuspiciousCardTransfers suspiciousCardTransfers : cardTransferEntityList ) {
            list.add( toDTO( suspiciousCardTransfers ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(CardTransfersDTO cardTransferDTO, SuspiciousCardTransfers cardTransferEntity) {
        if ( cardTransferDTO == null ) {
            return;
        }

        if ( cardTransferDTO.getId() != null ) {
            cardTransferEntity.setId( cardTransferDTO.getId() );
        }
        if ( cardTransferDTO.getCardTransferId() != null ) {
            cardTransferEntity.setCardTransferId( cardTransferDTO.getCardTransferId() );
        }
        cardTransferEntity.setBlocked( cardTransferDTO.isBlocked() );
        cardTransferEntity.setSuspicious( cardTransferDTO.isSuspicious() );
        if ( cardTransferDTO.getBlockedReason() != null ) {
            cardTransferEntity.setBlockedReason( cardTransferDTO.getBlockedReason() );
        }
        if ( cardTransferDTO.getSuspiciousReason() != null ) {
            cardTransferEntity.setSuspiciousReason( cardTransferDTO.getSuspiciousReason() );
        }
    }
}
