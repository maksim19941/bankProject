package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.PhoneTransfersDTO;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
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
public class PhoneTransferMapperImpl implements PhoneTransferMapper {

    @Override
    public SuspiciousPhoneTransfers toEntity(PhoneTransfersDTO phoneTransferDTO) {
        if ( phoneTransferDTO == null ) {
            return null;
        }

        SuspiciousPhoneTransfers.SuspiciousPhoneTransfersBuilder suspiciousPhoneTransfers = SuspiciousPhoneTransfers.builder();

        suspiciousPhoneTransfers.phone_transfer_id( phoneTransferDTO.getPhone_transfer_id() );
        suspiciousPhoneTransfers.blockedReason( phoneTransferDTO.getBlockedReason() );
        suspiciousPhoneTransfers.suspiciousReason( phoneTransferDTO.getSuspiciousReason() );

        return suspiciousPhoneTransfers.build();
    }

    @Override
    public PhoneTransfersDTO toDTO(SuspiciousPhoneTransfers phoneTransferEntity) {
        if ( phoneTransferEntity == null ) {
            return null;
        }

        PhoneTransfersDTO.PhoneTransfersDTOBuilder phoneTransfersDTO = PhoneTransfersDTO.builder();

        phoneTransfersDTO.id( phoneTransferEntity.getId() );
        phoneTransfersDTO.phone_transfer_id( phoneTransferEntity.getPhone_transfer_id() );
        phoneTransfersDTO.blockedReason( phoneTransferEntity.getBlockedReason() );
        phoneTransfersDTO.suspiciousReason( phoneTransferEntity.getSuspiciousReason() );

        return phoneTransfersDTO.build();
    }

    @Override
    public List<PhoneTransfersDTO> toDTOList(List<SuspiciousPhoneTransfers> phoneTransferEntityList) {
        if ( phoneTransferEntityList == null ) {
            return null;
        }

        List<PhoneTransfersDTO> list = new ArrayList<PhoneTransfersDTO>( phoneTransferEntityList.size() );
        for ( SuspiciousPhoneTransfers suspiciousPhoneTransfers : phoneTransferEntityList ) {
            list.add( toDTO( suspiciousPhoneTransfers ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(PhoneTransfersDTO phoneTransferDTO, SuspiciousPhoneTransfers phoneTransferEntity) {
        if ( phoneTransferDTO == null ) {
            return;
        }

        if ( phoneTransferDTO.getId() != null ) {
            phoneTransferEntity.setId( phoneTransferDTO.getId() );
        }
        if ( phoneTransferDTO.getPhone_transfer_id() != null ) {
            phoneTransferEntity.setPhone_transfer_id( phoneTransferDTO.getPhone_transfer_id() );
        }
        phoneTransferEntity.setBlocked( phoneTransferDTO.isBlocked() );
        phoneTransferEntity.setSuspicious( phoneTransferDTO.isSuspicious() );
        if ( phoneTransferDTO.getBlockedReason() != null ) {
            phoneTransferEntity.setBlockedReason( phoneTransferDTO.getBlockedReason() );
        }
        if ( phoneTransferDTO.getSuspiciousReason() != null ) {
            phoneTransferEntity.setSuspiciousReason( phoneTransferDTO.getSuspiciousReason() );
        }
    }
}
