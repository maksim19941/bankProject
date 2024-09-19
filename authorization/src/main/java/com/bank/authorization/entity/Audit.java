package com.bank.authorization.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "audit", schema = "auth")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @NotNull
    @Column(name = "entity_type", nullable = false, length = 40)
    private String entityType;

    @Size(max = 255)
    @NotNull
    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Size(max = 255)
    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Size(max = 255)
    @Column(name = "modified_by")
    private String modifiedBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "modified_at")
    private OffsetDateTime modifiedAt;

    @Column(name = "new_entity_json")
    private String newEntityJson;

    @NotNull
    @Column(name = "entity_json", nullable = false)
    private String entityJson;
}


