package com.bank.authorization.service;

import com.bank.authorization.entity.Audit;

import java.util.List;

public interface AuditService {
    List<Audit> getAudits();
    Audit getAudit(long id);
    Audit findByEntity_json(String json);
    void createAudit(Audit audit);
}

