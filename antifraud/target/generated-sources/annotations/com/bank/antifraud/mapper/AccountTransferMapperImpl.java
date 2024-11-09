package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.AccountTransfersDTO;
import com.bank.antifraud.model.SuspiciousAccountTransfers;
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
public class AccountTransferMapperImpl implements AccountTransferMapper {

    @Override
    public SuspiciousAccountTransfers toEntity(AccountTransfersDTO accountTransferDTO) {
        if ( accountTransferDTO == null ) {
            return null;
        }

        SuspiciousAccountTransfers.SuspiciousAccountTransfersBuilder suspiciousAccountTransfers = SuspiciousAccountTransfers.builder();

        suspiciousAccountTransfers.accountTransferId( accountTransferDTO.getAccountTransferId() );
        suspiciousAccountTransfers.blocked( accountTransferDTO.isBlocked() );
        suspiciousAccountTransfers.suspicious( accountTransferDTO.isSuspicious() );
        suspiciousAccountTransfers.blockedReason( accountTransferDTO.getBlockedReason() );
        suspiciousAccountTransfers.suspiciousReason( accountTransferDTO.getSuspiciousReason() );

        return suspiciousAccountTransfers.build();
    }

    @Override
    public AccountTransfersDTO toDTO(SuspiciousAccountTransfers accountTransfer) {
        if ( accountTransfer == null ) {
            return null;
        }

        AccountTransfersDTO.AccountTransfersDTOBuilder accountTransfersDTO = AccountTransfersDTO.builder();

        accountTransfersDTO.blocked( accountTransfer.isBlocked() );
        accountTransfersDTO.suspicious( accountTransfer.isSuspicious() );
        accountTransfersDTO.id( accountTransfer.getId() );
        accountTransfersDTO.accountTransferId( accountTransfer.getAccountTransferId() );
        accountTransfersDTO.blockedReason( accountTransfer.getBlockedReason() );
        accountTransfersDTO.suspiciousReason( accountTransfer.getSuspiciousReason() );

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
        if ( accountTransferDTO.getAccountTransferId() != null ) {
            accountTransferEntity.setAccountTransferId( accountTransferDTO.getAccountTransferId() );
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
