package com.budget.application.response.provider;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.service.ExpensesService;
import com.budget.application.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseResponseProvider {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private CommonTools commonTools;

    public ExpenseResponseEntity getAllExpenses() {
        ExpenseResponseEntity response = null;

        try {
            ExpensesList expenses = new ExpensesList(expensesService.getAllExpenses().get());
            response = new ExpenseResponseEntity(expenses, HttpStatus.OK);  // 200
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);    // 400
        }
        return response;
    }

    public ExpenseResponseEntity createExpense(Expense expense) {
        ExpenseResponseEntity response = null;
        try {
            ExpensesList expenses = new ExpensesList(Arrays.asList(expensesService.createExpense(expense)));
            response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.UNPROCESSABLE_ENTITY);  // 422
        }

        return response;
    }

    public ExpenseResponseEntity deleteExpense(Long expenseId) {
        ExpenseResponseEntity response = null;
        try {
            expensesService.deleteExpense(expenseId);
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public ExpenseResponseEntity getExpensesBySearchCriteria(List<String> tagNames, String fromDate, String toDate) {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        if (tagNames.size() > 0) {
            expensesSearchCriteria.setTagNames(tagNames);
        }
        if (StringUtils.isEmpty(fromDate) != true) {
            Timestamp fDate = null;
            try {
                fDate = commonTools.getTimeStampFromISODate(fromDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            expensesSearchCriteria.setFromDate(fDate);
        }
        if (StringUtils.isEmpty(toDate) != true) {
            Timestamp tDate = null;
            try {
                tDate = commonTools.getTimeStampFromISODate(toDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            expensesSearchCriteria.setToDate(tDate);
        }

        ExpenseResponseEntity response = null;

        try {
            ExpensesList expenses = new ExpensesList(expensesService.getExpensesBySearchCriteria(expensesSearchCriteria).get());
            response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
        } catch (Exception e) {
            response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);
        }

        return response;
    }
}
