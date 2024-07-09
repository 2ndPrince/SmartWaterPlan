package com.smart.water.plan.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<WeatherTransaction, String> {
}
