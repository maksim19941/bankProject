package com.bank.antifraud.aspect;

import com.bank.antifraud.model.AuditModel;
import com.bank.antifraud.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class AuditModelAspect {

    private final AuditService auditModelService;
    private final ObjectMapper objectMapper;

    public AuditModelAspect(AuditService auditModelService, ObjectMapper objectMapper) {
        this.auditModelService = auditModelService;
        this.objectMapper = objectMapper;
    }


    @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.save*(..))", returning = "result")
    public void afterResultCreateAdvice(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.warn("Result is null for method: {}", joinPoint.getSignature().getName());
            return;
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuditModel auditModel = new AuditModel();
        try {
            String entityJson = objectMapper.writeValueAsString(result);
            auditModel.setEntityType(result.getClass().getSimpleName());
            auditModel.setOperationType(methodSignature.getName());
            auditModel.setCreatedBy("Максим");
            auditModel.setCreatedAt(LocalDateTime.now());
            auditModel.setEntityJson(entityJson);
            auditModelService.createAudit(auditModel);

            log.info("Created auditModel record for entity: {}", result.getClass().getSimpleName());
        } catch (JsonProcessingException e) {
            log.error("Error auditModeling creation of record {}: {}", result, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.antifraud.service.*.update*(..))", returning = "result")
    public void afterResultUpdateAdvice(JoinPoint joinPoint, Object result) {
        if (result == null) {
            log.warn("Result is null for method: {}", joinPoint.getSignature().getName());
            return;
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuditModel auditModel = new AuditModel();
        try {
            String json = objectMapper.writeValueAsString(result);
            Optional<AuditModel> oldAuditModel = auditModelService.findFirstByEntityJsonStartingWith("{\"id\":" + objectMapper.readTree(json).get("id") + ",");

            if (oldAuditModel.isPresent()) {
                AuditModel oldAuditModelO = oldAuditModel.get();
                auditModel.setEntityType(result.getClass().getSimpleName());
                auditModel.setOperationType(methodSignature.getName());
                auditModel.setCreatedBy(oldAuditModelO.getCreatedBy());
                auditModel.setModifiedBy("Максим");
                auditModel.setCreatedAt(oldAuditModelO.getCreatedAt());
                auditModel.setModifiedAt(LocalDateTime.now());
                auditModel.setNewEntityJson(json);
                auditModel.setEntityJson(oldAuditModelO.getEntityJson());
                auditModelService.createAudit(auditModel);

                log.info("Updated auditModel record for entity: {}", result.getClass().getSimpleName());
            } else {
                log.warn("No previous auditModel record found for entity ID: {}", objectMapper.readTree(json).get("id"));
            }
        } catch (JsonProcessingException e) {
            log.error("Error auditModeling update of record {}: {}", result, e.getMessage());
            throw new RuntimeException(e);
        }
    }




}