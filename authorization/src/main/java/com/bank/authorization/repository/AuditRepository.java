package com.bank.authorization.repository;

import com.bank.authorization.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    @Query("SELECT e FROM Audit e WHERE e.new_entity_json = :json")
    Audit findByNew_entity_json(@Param("json") String json);
}
