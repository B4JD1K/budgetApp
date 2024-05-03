package com.budget.application.service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface ExpensesService {

    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria);

    public Expense createExpense(Expense expense);

    public Optional<List<Expense>> getAllExpenses();

    public void deleteExpense(Long expenseId);
}
