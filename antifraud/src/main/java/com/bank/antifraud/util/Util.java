package com.bank.antifraud.util;

import com.bank.antifraud.model.SuspiciousAccountTransfers;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import com.bank.antifraud.model.SuspiciousPhoneTransfers;
import com.bank.antifraud.repository.SuspicionsPhoneTransfersRepository;
import com.bank.antifraud.repository.SuspiciousAccountTransfersRepository;
import com.bank.antifraud.repository.SuspiciousCardTransfersRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Util {

    private final SuspiciousAccountTransfersRepository repositoryAcc;
    private final SuspicionsPhoneTransfersRepository repositoryPh;
    private final SuspiciousCardTransfersRepository repositoryCard;

    public Util(SuspiciousAccountTransfersRepository repositoryAcc, SuspicionsPhoneTransfersRepository repositoryPh, SuspiciousCardTransfersRepository repositoryCard) {
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
                    .account_transfer_id(213123L + i)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .blockedReason("fraud " + i)
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryAcc.save(accountTransfers);
        }
    }

    private void createCardTransfers() {
        for (long i = 1; i <= 5; i++) {
            SuspiciousCardTransfers cardTransfers = SuspiciousCardTransfers.builder()
                    .id(i)
                    .card_transfer_id(4234234L + i)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .blockedReason("fraud " + i)
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryCard.save(cardTransfers);
        }
    }

    private void createPhoneTransfers() {
        for (long i = 1; i <= 5; i++) {
            SuspiciousPhoneTransfers phoneTransfers = SuspiciousPhoneTransfers.builder()
                    .id(i)
                    .phone_transfer_id(4234235L + i)
                    .isBlocked(false)
                    .isSuspicious(false)
                    .blockedReason("fraud " + i)
                    .suspiciousReason("fraud " + i)
                    .build();

            repositoryPh.save(phoneTransfers);
        }
    }



}
