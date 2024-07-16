package com.smart.water.plan.transaction;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Async
    public WeatherTransaction saveTransaction(WeatherTransaction transaction) {
        return transactionRepository.save(transaction);
    }
}
