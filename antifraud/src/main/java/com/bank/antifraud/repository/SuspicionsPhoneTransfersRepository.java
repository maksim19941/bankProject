package com.bank.antifraud.repository;

import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuspicionsPhoneTransfersRepository extends JpaRepository<SuspiciousPhoneTransfers, Long> {
}
