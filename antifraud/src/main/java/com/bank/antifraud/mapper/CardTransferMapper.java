package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CardTransferMapper {

    CardTransferMapper INSTANCE = Mappers.getMapper(CardTransferMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "card_transfer_id", source = "cardTransferDTO.card_transfer_id")
    SuspiciousCardTransfers toEntity(CardTransfersDTO cardTransferDTO);

    CardTransfersDTO toDTO(SuspiciousCardTransfers cardTransferEntity);

    List<CardTransfersDTO> toDTOList(List<SuspiciousCardTransfers> cardTransferEntityList);

    void updateEntityFromDTO(CardTransfersDTO cardTransferDTO, @MappingTarget SuspiciousCardTransfers cardTransferEntity);
}
