package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;

import java.util.List;
import java.util.Optional;

public interface AuditService {
    List<Audit> getAudits();
    Audit getAudit(long id);
    Optional<Audit> findAuditByJson(String json);
    void createAudit(Audit audit);
}

