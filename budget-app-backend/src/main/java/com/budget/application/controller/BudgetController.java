package com.budget.application.controller;

import com.budget.application.model.Expense;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BudgetController {


    //ExpensesProvider
    //TagsProvider
    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    public Object addExpense(@RequestBody Expense expense) {

        return null;
    }

    @RequestMapping(value = "/expense/{expenseId}", method = RequestMethod.DELETE)
    public Object deleteExpense(@PathVariable(value = "expenseId") Long expenseId) {

        return null;
    }

    @RequestMapping(value = "/expenses", method = RequestMethod.GET)
    public Object getAllExpenses() {

        return null;
    }

    @RequestMapping(value = "/expense/criteria", method = RequestMethod.GET)
    public Object getExpensesBySearchCriteria(@RequestParam(value = "tagNames") List<String> tagNames,
                                              @RequestParam(value = "fromDate") String fromDate,
                                              @RequestParam(value = "toDate") String toomDate) {

        return null;
    }

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    public Object getAllTags() {

        return null;
    }

    @RequestMapping(value = "tag",method = RequestMethod.POST)
    public Object addNewTag(@RequestBody String name){

        return null;
    }

    @RequestMapping(value = "tag/{tagId}", method = RequestMethod.DELETE)
    public Object deleteTag(@PathVariable("tagId") Long tagId){

        return null;
    }
}
