package com.bank.authorization.repository;

import com.bank.authorization.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    Optional<Audit>  findFirstByEntityJsonStartingWithOrderByModifiedAt(String json);
}
