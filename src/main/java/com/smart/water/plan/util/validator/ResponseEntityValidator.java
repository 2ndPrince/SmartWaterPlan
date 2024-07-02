package com.smart.water.plan.util.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseEntityValidator {
    public static void validateResponse(ResponseEntity<?> responseEntity) {
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Error: HTTP status code is not OK. Status code: {}", responseEntity.getStatusCode());
        }
        // TODO: throw exception to retry the request
    }
}
