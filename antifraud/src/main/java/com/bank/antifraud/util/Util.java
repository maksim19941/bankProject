package com.bank.antifraud.util;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspiciousPhoneTransfersRepository;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Util {

    private final SuspiciousAccountTransfersRepository repositoryAcc;
    private final SuspiciousPhoneTransfersRepository repositoryPh;
    private final SuspiciousCardTransfersRepository repositoryCard;

    public Util(SuspiciousAccountTransfersRepository repositoryAcc, SuspiciousPhoneTransfersRepository repositoryPh, SuspiciousCardTransfersRepository repositoryCard) {
        this.repositoryAcc = repositoryAcc;
        this.repositoryPh = repositoryPh;
        this.repositoryCard = repositoryCard;
    }


    @PostConstruct
    private void post() {
        createAccountTransfers();
        createCardTransfers();
        createPhoneTransfers();
    }

    private void createAccountTransfers() {
        for (long i = 1; i <= 5; i++) {
            SuspiciousAccountTransfers accountTransfers = SuspiciousAccountTransfers.builder()
                    .id(i)
                    .accountTransferId(213123L + i)
                    .blocked(true)
                    .suspicious(true)
                    .blockedReason("dfghfgh")
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryAcc.save(accountTransfers);
        }
    }

    private void createCardTransfers() {
        for (long i = 1; i <= 5; i++) {
            SuspiciousCardTransfers cardTransfers = SuspiciousCardTransfers.builder()
                    .id(i)
                    .cardTransferId(213123L + i)
                    .blocked(true)
                    .suspicious(true)
                    .blockedReason("dfghfgh")
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryCard.save(cardTransfers);
        }
    }

    private void createPhoneTransfers() {
        for (long i = 1; i <= 5; i++) {
            SuspiciousPhoneTransfers phoneTransfers = SuspiciousPhoneTransfers.builder()
                    .id(i)
                    .phoneTransferId(213123L + i)
                    .blocked(true)
                    .suspicious(true)
                    .blockedReason("dfghfgh")
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryPh.save(phoneTransfers);
        }
    }
}
