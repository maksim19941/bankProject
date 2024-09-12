package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;
import com.bank.authorization.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Override
    public List<Audit> getAudits() {
        try {
            return auditRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Audit getAudit(long id) {
        try {
            return auditRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Audit findByEntity_json(String json) {
        try {
            return auditRepository.findByNew_entity_json(json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void createAudit(Audit audit) {
        try {
            auditRepository.save(audit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
