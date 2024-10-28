package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
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
public class AccountTransferMapperImpl implements AccountTransferMapper {

    @Override
    public SuspiciousAccountTransfers toEntity(AccountTransfersDTO accountTransferDTO) {
        if ( accountTransferDTO == null ) {
            return null;
        }

        SuspiciousAccountTransfers.SuspiciousAccountTransfersBuilder suspiciousAccountTransfers = SuspiciousAccountTransfers.builder();

        suspiciousAccountTransfers.account_transfer_id( accountTransferDTO.getAccount_transfer_id() );
        suspiciousAccountTransfers.blockedReason( accountTransferDTO.getBlockedReason() );
        suspiciousAccountTransfers.suspiciousReason( accountTransferDTO.getSuspiciousReason() );

        return suspiciousAccountTransfers.build();
    }

    @Override
    public AccountTransfersDTO toDTO(SuspiciousAccountTransfers accountTransferEntity) {
        if ( accountTransferEntity == null ) {
            return null;
        }

        AccountTransfersDTO.AccountTransfersDTOBuilder accountTransfersDTO = AccountTransfersDTO.builder();

        accountTransfersDTO.id( accountTransferEntity.getId() );
        accountTransfersDTO.account_transfer_id( accountTransferEntity.getAccount_transfer_id() );
        accountTransfersDTO.blockedReason( accountTransferEntity.getBlockedReason() );
        accountTransfersDTO.suspiciousReason( accountTransferEntity.getSuspiciousReason() );

        return accountTransfersDTO.build();
    }

    @Override
    public List<AccountTransfersDTO> toDTOList(List<SuspiciousAccountTransfers> accountTransferEntityList) {
        if ( accountTransferEntityList == null ) {
            return null;
        }

        List<AccountTransfersDTO> list = new ArrayList<AccountTransfersDTO>( accountTransferEntityList.size() );
        for ( SuspiciousAccountTransfers suspiciousAccountTransfers : accountTransferEntityList ) {
            list.add( toDTO( suspiciousAccountTransfers ) );
        }

        return list;
    }

    @Override
    public void updateEntityFromDTO(AccountTransfersDTO accountTransferDTO, SuspiciousAccountTransfers accountTransferEntity) {
        if ( accountTransferDTO == null ) {
            return;
        }

        if ( accountTransferDTO.getId() != null ) {
            accountTransferEntity.setId( accountTransferDTO.getId() );
        }
        if ( accountTransferDTO.getAccount_transfer_id() != null ) {
            accountTransferEntity.setAccount_transfer_id( accountTransferDTO.getAccount_transfer_id() );
        }
        accountTransferEntity.setBlocked( accountTransferDTO.isBlocked() );
        accountTransferEntity.setSuspicious( accountTransferDTO.isSuspicious() );
        if ( accountTransferDTO.getBlockedReason() != null ) {
            accountTransferEntity.setBlockedReason( accountTransferDTO.getBlockedReason() );
        }
        if ( accountTransferDTO.getSuspiciousReason() != null ) {
            accountTransferEntity.setSuspiciousReason( accountTransferDTO.getSuspiciousReason() );
        }
    }
}
