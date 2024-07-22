package com.budget.application.controller;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.ExpenseResponseEntity;
import com.budget.application.response.provider.ExpenseResponseProvider;
import com.budget.application.response.provider.TagResponseEntity;
import com.budget.application.response.provider.TagResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BudgetController {

    @Autowired
    private ExpenseResponseProvider expenseResponseProvider;

    @Autowired
    private TagResponseProvider tagResponseProvider;

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    public ExpenseResponseEntity addExpense(@RequestBody Expense expense) {
        return expenseResponseProvider.createExpense(expense);
    }

    @RequestMapping(value = "/expense/{expenseId}", method = RequestMethod.DELETE)
    public ExpenseResponseEntity deleteExpense(@PathVariable(value = "expenseId") Long expenseId) {
        return expenseResponseProvider.deleteExpense(expenseId);
    }

    @RequestMapping(value = "/expenses", method = RequestMethod.GET)
    public ExpenseResponseEntity getAllExpenses() {
        return expenseResponseProvider.getAllExpenses();
    }

    @RequestMapping(value = "/expense/criteria", method = RequestMethod.GET)
    public ExpenseResponseEntity getExpensesBySearchCriteria(@RequestParam(value = "tagNames") List<String> tagNames,
                                                             @RequestParam(value = "fromDate") String fromDate,
                                                             @RequestParam(value = "toDate") String toomDate) {
        return expenseResponseProvider.getExpensesBySearchCriteria(tagNames, fromDate, toomDate);
    }

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public TagResponseEntity getAllTags() {
        return tagResponseProvider.getAllTags();
    }

    @RequestMapping(value = "tag", method = RequestMethod.POST)
    public TagResponseEntity addNewTag(@RequestBody String tagName) {
        return tagResponseProvider.createTag(tagName);
    }

    @RequestMapping(value = "tag/{tagId}", method = RequestMethod.DELETE)
    public TagResponseEntity deleteTag(@PathVariable("tagId") Long tagId) {
        return tagResponseProvider.deleteTag(tagId);
    }
}
