package com.budget.application.response.provider;

import com.budget.application.model.Expense;

import java.util.List;

public class ExpensesList {
    private List<Expense> expenses;

    public ExpensesList() {
        super();
    }

    public ExpensesList(List<Expense> expenses) {
        super();
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
