package com.bank.authorization.controller;

import com.bank.authorization.entity.Audit;
import com.bank.authorization.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    public List<Audit> getAudits() {
        return auditService.getAudits();
    }

    @GetMapping("/{id}")
    public Audit getAudit(@PathVariable long id) {
        return auditService.getAudit(id);
    }
}

