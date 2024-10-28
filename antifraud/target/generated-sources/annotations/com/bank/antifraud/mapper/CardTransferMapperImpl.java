package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-27T15:21:58+0300",
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

        suspiciousCardTransfers.card_transfer_id( cardTransferDTO.getCard_transfer_id() );
        suspiciousCardTransfers.blockedReason( cardTransferDTO.getBlockedReason() );
        suspiciousCardTransfers.suspiciousReason( cardTransferDTO.getSuspiciousReason() );

        return suspiciousCardTransfers.build();
    }

    @Override
    public CardTransfersDTO toDTO(SuspiciousCardTransfers cardTransferEntity) {
        if ( cardTransferEntity == null ) {
            return null;
        }

        CardTransfersDTO.CardTransfersDTOBuilder cardTransfersDTO = CardTransfersDTO.builder();

        cardTransfersDTO.id( cardTransferEntity.getId() );
        cardTransfersDTO.card_transfer_id( cardTransferEntity.getCard_transfer_id() );
        cardTransfersDTO.blockedReason( cardTransferEntity.getBlockedReason() );
        cardTransfersDTO.suspiciousReason( cardTransferEntity.getSuspiciousReason() );

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
        if ( cardTransferDTO.getCard_transfer_id() != null ) {
            cardTransferEntity.setCard_transfer_id( cardTransferDTO.getCard_transfer_id() );
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
