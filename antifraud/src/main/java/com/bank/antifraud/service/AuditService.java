package com.bank.antifraud.service;

import com.bank.antifraud.model.AuditModel;

import java.util.List;
import java.util.Optional;

public interface AuditService {


    void createAudit(AuditModel audit);

    Optional<AuditModel> findFirstByEntityJsonStartingWith(String json);

    AuditModel findById(Long id);

    List<AuditModel> findAll();

    void deleteById(Long id);
}
