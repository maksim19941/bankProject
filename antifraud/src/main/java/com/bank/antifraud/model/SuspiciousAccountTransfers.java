package com.bank.antifraud.model;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NotNull
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suspicious_account_transfers", schema = "anti_fraud")
public class SuspiciousAccountTransfers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_transfer_id")
    private Long account_transfer_id;
    private boolean isBlocked;
    private boolean isSuspicious;
    @Nullable
    private String blockedReason;
    private String suspiciousReason;
}
