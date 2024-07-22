package com.budget.application.response.provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExpenseResponseEntity extends ResponseEntity<ExpensesList> {

    public ExpenseResponseEntity(ExpensesList body, HttpStatus httpStatus) {
        super(body, httpStatus);
    }
}
