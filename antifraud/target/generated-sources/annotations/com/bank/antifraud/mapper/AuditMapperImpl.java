package com.bank.antifraud.mapper;

import com.bank.antifraud.model.AuditModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-27T15:21:58+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class AuditMapperImpl implements AuditMapper {

    @Override
    public AuditModel toEntity(AuditModel updateAudit) {
        if ( updateAudit == null ) {
            return null;
        }

        AuditModel.AuditModelBuilder auditModel = AuditModel.builder();

        auditModel.entityType( updateAudit.getEntityType() );
        auditModel.operationType( updateAudit.getOperationType() );
        auditModel.createdBy( updateAudit.getCreatedBy() );
        auditModel.modifiedBy( updateAudit.getModifiedBy() );
        auditModel.createdAt( updateAudit.getCreatedAt() );
        auditModel.modifiedAt( updateAudit.getModifiedAt() );
        auditModel.newEntityJson( updateAudit.getNewEntityJson() );
        auditModel.entityJson( updateAudit.getEntityJson() );

        return auditModel.build();
    }
}
