package com.bank.antifraud;

import com.bank.antifraud.dto.CardTransfersDTO;
import com.bank.antifraud.model.SuspiciousCardTransfers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;


@SpringBootApplication
@EnableDiscoveryClient
public class AntifraudApplication {
    public static void main(String[] args) {

        SpringApplication.run(AntifraudApplication.class, args);

    }
}


