package com.budget.application.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.budget.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.ExpensesList;
import com.budget.application.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BudgetControllerTestWithSpringRunner {


    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    private TestUtils testUtils = new TestUtils();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testAddExpense() {
        Expense testExpenseToAdd = testUtils.generateTestExpense(1, null);
        HttpEntity<Expense> entity = new HttpEntity<Expense>(testExpenseToAdd, headers);
        ResponseEntity<ExpensesList> response = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, entity, ExpensesList.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(response.getBody().getExpenses().size()>0);
    }

    @Test
    public void testGetExpenses() {
        Expense testExpenseToAdd = testUtils.generateTestExpense(1, null);
        HttpEntity<Expense> createExpenseEntity = new HttpEntity<Expense>(testExpenseToAdd, headers);
        ResponseEntity<ExpensesList> createExpenseResponse = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, createExpenseEntity, ExpensesList.class);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<ExpensesList> response = restTemplate.exchange(createURLWithPort("/expenses"), HttpMethod.GET, entity, ExpensesList.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        assertTrue(response.getBody().getExpenses().size()>0);
    }

    @Test
    public void testAddNewTag() {
        fail("Not implemented yet.");
    }

    @Test
    public void testDeleteTag() {
        fail("Not implemented yet.");
    }

    @Test
    public void testDeleteExpense() {
        fail("Not implemented yet.");
    }

    @Test
    public void testGetTags() {
        fail("Not implemented yet.");

    }

    @Test
    public void testGetExpensesBySearchCriteriaWithTagNamesSettedOnly() throws Exception {
        fail("Not implemented yet.");
    }
}
