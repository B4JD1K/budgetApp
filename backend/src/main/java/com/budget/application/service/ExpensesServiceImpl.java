package com.budget.application.service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria) {

        List<Expense> filteredExpenses = new ArrayList<Expense>();
        List<Expense> allExpenses = expenseRepository.findAll();
        filteredExpenses = allExpenses;
        // powyzsze przypisanie wszystkich wydatkow do zafiltrowanej listy zmieniło nieco poniższy opis

        if (criteria.getTagNames() != null) {
            // przekształcamy listę wejściową 'allExpenses' do strumienia.
            //  pozwala nam to na strumieniu wywołać metodę filtrowania
            filteredExpenses = filteredExpenses.stream()
                    // wywoływując na strumieniu metodę filtrowania, której predykatem jest wyrażenie następujące:
                    //  dla każdego 'expense' ze strumienia 'allExpenses' pobieramy listę tagów expense.getTags(), którą
                    //   przekształcamy do strumienia, na którym stosujemy kolejny predykat
                    .filter(expense -> expense.getTags().stream()
                            // który sprawdza czy zachodzi jakakolwiek zgodność pomiędzy którymś z tagów ze strumienia
                            //  wyżej, a tagami, które znajdują się w przekazanych kryteriach 'criteria' (linijka 26).
                            //   Jeżeli któryś z wydatków 'expenses' zawiera jakikolwiek tag z kryteriów, to wtedy
                            .anyMatch(tag -> criteria.getTagNames().contains(tag.getName())))
                    // listę wynikową tych wydatków z zbieramy za pomocą wyrażenia 'collect', a 'Collectors.toList()'
                    //  pozwala nam na zebranie wyników całej operacji, która określona jest w tych trzech linijkach,
                    //   do listy 'filteredExpenses' (linijka 34), ponieważ 'Collectors.toList()' jest metodą, która
                    //    zwraca listę obiektów na podstawie strumienia wejściowego, którą następnie przypisujemy do
                    //     utworzonej listy 'filteredExpenses'
                    .collect(Collectors.toList());
        }

        if (criteria.getFromDate() != null) {
            // kluczowa rzecz!!!
            //  operujemy teraz na liście wcześniej przefiltrowanych wydatków, które wyszukiwaliśmy przy pomocy tagów,
            //   tak więc przefiltrowaną już listę 'filteredExpenses' przekształcamy do strumienia
            filteredExpenses = filteredExpenses.stream()
                    // wykonujemy operację filtrowania na tej liście. dla każdego wydatku, każdy wydatek 'expense'
                    //  sprawdzamy czy jego data utworzenia jest po (.getCreationDate().isAfter(criteria...)) kryteriach
                    //   określonych w linijce 26. te kryteria przekształcane są od daty i ustawiane według formatu PC
                    .filter(expense -> expense.getCreationDate().isAfter(criteria.getFromDate().toLocalDateTime()))
                    // a następnie otrzymany wynik z tych powyższych kryteriów zbieramy w całość do listy (jak powyżej)
                    //  i przypisujemy kolejny wynik z zafiltrowanymi kolejnymi kryteriami do listy 'filteredExpenses'
                    .collect(Collectors.toList());
        }

        if (criteria.getToDate() != null) {
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense.getCreationDate().isBefore(criteria.getToDate().toLocalDateTime()))
                    .collect(Collectors.toList());
        }

        return Optional.of(filteredExpenses);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public Optional<List<Expense>> getAllExpenses() {
        List<Expense> allExpenses = expenseRepository.findAll();
        return Optional.of(allExpenses);
    }
}