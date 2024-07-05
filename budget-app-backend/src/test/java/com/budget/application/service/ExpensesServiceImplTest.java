package com.budget.application.service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.CommonTools;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpensesServiceImplTest {

    @InjectMocks
    private ExpensesServiceImpl expensesService;

    @Mock
    private ExpenseRepository expenseRepository;

    private TestUtils testUtils;

    @Mock
    private CommonTools tools;

    private Timestamp fromDate, toDate;

    private List<Expense> generatedExpenses;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testUtils = new TestUtils();
        generatedExpenses = testUtils.generateGivenAmounOfTestExpenseObjects(5, 5, Timestamp.valueOf("2018-11-12 01:00:00.123456789"));
        fromDate = Timestamp.valueOf("2018-11-01 01:02:03.123456789");
        toDate = Timestamp.valueOf("2018-12-12 01:02:03.123456789");
        testExpense = testUtils.generateGivenAmounOfTestExpenseObjects(5, 5, Timestamp.valueOf("2018-11-10 01:02:03.123456789")).get(0);

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(testExpense);
        Mockito.when(expenseRepository.findAll()).thenReturn(generatedExpenses);
    }


    @Test
    void createExpense() {
        Expense createdExpense = null;
        createdExpense = expensesService.createExpense(testExpense);
        assertNotNull(createdExpense);
        assertEquals(createdExpense.getValue(), testExpense.getValue());
    }

    @Test
    void getExpensesByCriteriaWithTagsSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        // to jest ostatni krok powiązany z Collectors.toList();
        List<String> tagNames =
                // z wygenerowanych wdydatków pobieramy "pewien wydate", a następnie jego "tagi"
                generatedExpenses.get(0).getTags()
                        // przekształcamy go do strumienia, a na strumieniu wywołujemy mapowianie.
                        // wenątrz map jesteśmy w stanie wywołać metodę "getName()" z klasy "Tag"
                        // w klasie "public class Tag" jest metoda "getName() { return name; }"
                        // która zwraca nazwę taga. wynik ten operacji, tj:
                        // pobranie wydatku i wszystkie jego tagi
                        .stream().map(Tag::getName)
                        // zbieramy do kolekcji jaki listę
                        // --linia68-- w ten sposób dostajemy listę stringów (List<String>) dla danego wydatku
                        .collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println(foundExpenses.get().size());
        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(toDate);
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println(foundExpenses.get().size());
        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithBothDateSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println(foundExpenses.get().size());
        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void getExpensesByCriteriaWithBothDateAndTagsSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);
        List<String> tagNames = generatedExpenses.get(0).getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);
        System.out.println(foundExpenses.get().size());
        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void deleteExpense() {
        Expense expense = expenseRepository.findAll().get(0);
        expensesService.deleteExpense(expense.getId());
        Optional<Expense> foundByIdAfterDelete = expenseRepository.findById(expense.getId());
        assertFalse(foundByIdAfterDelete.isPresent());
    }

    @Test
    void getAllExpenses() {
        Optional<List<Expense>> allExpenses = expensesService.getAllExpenses();
        assertEquals(allExpenses.get().size(), generatedExpenses.size());
    }
}