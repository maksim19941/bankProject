package com.bank.authorization.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "audit", schema = "auth")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String entity_type;
    @Column(nullable = false)
    private String operation_type;
    @Column(nullable = false)
    private String created_by;
    private String modified_by;
    @Column(nullable = false)
    private Timestamp created_at;
    private Timestamp modified_at;
    private String new_entity_json;
    @Column(nullable = false)
    private String entity_json;

}

