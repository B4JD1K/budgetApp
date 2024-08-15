package com.budget.application.repository;

import com.budget.application.model.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    Optional<Expense> findById(Long id);

    Expense save(Expense expense);

    List<Expense> findAll();
}
