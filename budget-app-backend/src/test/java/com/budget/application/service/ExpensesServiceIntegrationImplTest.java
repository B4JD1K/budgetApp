package com.budget.application.service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExpensesServiceIntegrationImplTest {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private ExpenseRepository expenseRepository;

    private TestUtils testUtils;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testUtils = new TestUtils();
        testExpense = testUtils.generateTestExpense(1, LocalDateTime.now());
        for (int i = 0; i < 10; i++) {
            expensesService.createExpense(testUtils.generateTestExpense(1, LocalDateTime.now()));
        }
    }

    @Test
    void createExpense() {
        Expense expense = expensesService.createExpense(testExpense);
        System.out.println("expense.getValue() = " + expense.getValue());
        System.out.println("testExpense.getValue() = " + testExpense.getValue());
        assertEquals(expense.getValue(), testExpense.getValue());
    }

    @Test
    void deleteExpense() {
        Long retrievedExpenseId = expensesService.getAllExpenses().get().get(0).getId();
        System.out.println("retrievedExpenseId = " + retrievedExpenseId);
        Optional<Expense> foundById = expenseRepository.findById(retrievedExpenseId);
        System.out.println("foundById = " + foundById);
        expensesService.deleteExpense(retrievedExpenseId);
        assertFalse(!foundById.isPresent());

    }

    @Test
    void getAllExpenses() {
        List<Expense> allExpenses = expensesService.getAllExpenses().get();
//        System.out.println("expensesService.getAllExpenses().get().size() = " +expensesService.getAllExpenses().get().size());
//        assertTrue(!expensesService.getAllExpenses().get().isEmpty());
        System.out.println("allExpenses.size() = " + allExpenses.size());
        assertTrue(allExpenses.size() >= 10);
    }

    @Test
    void getExpensesByCriteriaWithTagsSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        List<String> tagNames = expensesService.getAllExpenses().get().get(0).getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> expensesRetrievedByCriteria = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println("expensesRetrievedByCriteria.isPresent() = " + expensesRetrievedByCriteria.isPresent());
        assertTrue(expensesRetrievedByCriteria.isPresent());
        System.out.println("expensesRetrievedByCriteria.get().size() = " + expensesRetrievedByCriteria.get().size());
        assertTrue(expensesRetrievedByCriteria.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime fromDate = expensesService.getAllExpenses().get().get(0).getCreationDate();
        fromDate = fromDate.minusSeconds(1);
        System.out.println("fromDate = " + fromDate);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        Optional<List<Expense>> expensesRetrievedByCriteria = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetrievedByCriteria.isPresent());
        assertTrue(expensesRetrievedByCriteria.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime toDate = expensesService.getAllExpenses().get().get(0).getCreationDate();
        toDate = toDate.plusSeconds(5);
        System.out.println("toDate = " + toDate);
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        Optional<List<Expense>> expensesRetrievedByCriteria = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(expensesRetrievedByCriteria.isPresent());
        assertTrue(expensesRetrievedByCriteria.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithBothDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        LocalDateTime creationDate = expensesService.getAllExpenses().get().get(0).getCreationDate();
        System.out.println("creationDate = " + creationDate);
        LocalDateTime toDate = creationDate.plusSeconds(5);
        LocalDateTime fromDate = creationDate.minusSeconds(5);
        System.out.println("toDate = " + toDate);
        System.out.println("fromDate = " + fromDate);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        Optional<List<Expense>> expensesRetrievedByCriteria = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println("expensesRetrievedByCriteria = " + expensesRetrievedByCriteria);
        assertTrue(expensesRetrievedByCriteria.isPresent());
        assertTrue(expensesRetrievedByCriteria.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithBothDateAndTagsSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        Expense retrievedExpense = expensesService.getAllExpenses().get().get(0);
        System.out.println("retrievedExpense = " + retrievedExpense);
        LocalDateTime creationDate = retrievedExpense.getCreationDate();
        System.out.println("creationDate = " + creationDate);
        LocalDateTime toDate = creationDate.plusSeconds(5);
        LocalDateTime fromDate = creationDate.minusSeconds(5);
        System.out.println("toDate = " + toDate);
        System.out.println("fromDate = " + fromDate);
        expensesSearchCriteria.setFromDate(Timestamp.valueOf(fromDate));
        expensesSearchCriteria.setToDate(Timestamp.valueOf(toDate));
        List<String> tagNames = retrievedExpense.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        System.out.println("tagNames = " + tagNames);
        Optional<List<Expense>> expensesRetrievedByCriteria = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println("expensesRetrievedByCriteria = " + expensesRetrievedByCriteria);
        assertTrue(expensesRetrievedByCriteria.isPresent());
        assertTrue(expensesRetrievedByCriteria.get().size() > 0);
    }
}