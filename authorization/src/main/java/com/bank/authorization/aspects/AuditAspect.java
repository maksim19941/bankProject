package com.bank.authorization.aspects;

import com.bank.authorization.entity.Audit;
import com.bank.authorization.entity.User;
import com.bank.authorization.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.Optional;


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* com.bank.authorization.service.*.save*(..))", returning = "user")
    public void auditSave(JoinPoint joinPoint, User user) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(user);
            Audit audit = new Audit();
            audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
            audit.setOperationType(signature.getMethod().getName());
            audit.setCreatedBy(objectMapper.readTree(json).get("profileId").asText());
            audit.setModifiedBy(null);
            audit.setCreatedAt(OffsetDateTime.now());
            audit.setModifiedAt(null);
            audit.setNewEntityJson(null);
            audit.setEntityJson(json);
            auditService.createAudit(audit);
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта User", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.bank.authorization.service.*.update*(..))", returning = "user")
    public void auditUpdate(JoinPoint joinPoint, User user) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            String json = objectMapper.writeValueAsString(user);
            Optional<Audit> oldAudit = auditService.findAuditByJson("{\"id\":" + objectMapper.readTree(json).get("id") + ",");

            if (oldAudit.isPresent()) {
                Audit audit = new Audit();
                Audit oldAuditO = oldAudit.get();
                audit.setEntityType(signature.getParameterTypes()[0].getSimpleName());
                audit.setOperationType(signature.getMethod().getName());
                audit.setCreatedBy(oldAuditO.getCreatedBy());
                audit.setModifiedBy(objectMapper.readTree(json).get("profileId").asText());
                audit.setCreatedAt(oldAuditO.getCreatedAt());
                audit.setModifiedAt(OffsetDateTime.now());
                audit.setNewEntityJson(json);
                audit.setEntityJson(oldAuditO.getEntityJson());
                auditService.createAudit(audit);
            }
        } catch (JsonProcessingException e) {
            log.error("Ошибка сериализации объекта User", e);
        }
    }
}