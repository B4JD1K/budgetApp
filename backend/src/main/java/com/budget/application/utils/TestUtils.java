package com.budget.application.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import com.budget.application.model.Expense;
import com.budget.application.model.Tag;


/**
 * Set of methods used for Junit tests
 * 
 * @author Marcin
 *
 */

public class TestUtils {
	
	public String getRequestParamStringFromArray(String paramName, List<String> paramValues, int maximumAmountOfParams) {
		StringBuilder sb = new StringBuilder();
		List<String> targetList = new ArrayList<>(paramValues);
		
		for(int i=0; i<maximumAmountOfParams; i++) {
			String currentParamValue = targetList.get(i);
			sb.append(paramName+"="+ currentParamValue + "&");
		}
		
		String result = sb.toString();
		if(result.endsWith("&")) {
			result = result.substring(0, result.length()-1);
		}
		
		return result;
	}
	
	public String getRandomTextFromUUID() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 5);
	}

	/**
	 * Adds to List from the first param elements from second param
	 * @param <T>
	 * @param list
	 * @param args
	 * @return
	 */
	public <T> List<T> append(List<T> list, T... args) {
		return Stream.concat(list.stream(), Stream.of(args)).collect(Collectors.toList());
	}

	public byte[] getBytesArrayFromJSON(JSONObject object) throws IOException {
		byte[] bytes = object.toString().getBytes();
		return bytes;
	}

	/**
	 * 
	 * @param amountOfTagsToGenerate
	 * @param expenseDate
	 * @return
	 */
	public Expense generateTestExpense(int amountOfTagsToGenerate, LocalDateTime expenseDate) {
		Expense expense = new Expense();
		if (expenseDate == null) {
			expense.setCreationDate(LocalDateTime.now());
		} else {
			expense.setCreationDate(expenseDate );
		}
		expense.setTags(generateTestTags(amountOfTagsToGenerate, false));
		expense.setValue(getRandomDoubleFromGivenRange(0.0, 10000));

		return expense;
	}

	public List<Tag>  generateTestTags(int amountOfTagsToGenerate, boolean createIds) {
		List<Tag> randomTags = new ArrayList<Tag>();
		for (int i = 0; i < amountOfTagsToGenerate; i++) {
			randomTags.add(new Tag(UUID.randomUUID().toString().substring(0, 10)));
		}
		return randomTags;
	}

	public double getRandomDoubleFromGivenRange(double rangeMin, double rangeMax) {
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}

	public List<Expense> generateGivenAmounOfTestExpenseObjects(int amountOfExpenses, int amountOfTagsInEachExpense,
			Timestamp expenseDate) {
		List<Expense> expenses = new ArrayList<Expense>();
		for (int i = 0; i < amountOfExpenses; i++) {
			expenses.add(generateTestExpense(amountOfTagsInEachExpense, LocalDateTime.now()));
		}
		return expenses;
	}

	/**
	 * Returns Timestamp date object from given date string in given format
	 * (consistent with SimpleDateFormat)
	 * 
	 * @param date
	 * @param format (for example: "yyyy-MMM-dd")
	 * @return
	 * @throws ParseException
	 */
	public Timestamp getDateFromGivenFormat(String dateInProperFormat, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat();
		Date date = formatter.parse(dateInProperFormat);
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	/**
	 * Returns LocalDateTime object from given Date.
	 * 
	 * @param dateInProperFormat (f.e : "2016-03-04 11:30")
	 * @param format             (f.e: "yyyy-MM-dd HH:mm")
	 * @return
	 */
	public LocalDateTime getLocalDateTimeFromGivenFormat(String dateInProperFormat, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime dateTime = LocalDateTime.parse(dateInProperFormat, formatter);
		return dateTime;
	}
	
	/**
	 * Returns ISO 8601 String based on input LocalDateTime date
	 * @param localDateTime
	 * @return String (ISO 8601 formatted date)
	 */
	public String getISOStringFromLocalDateTime(LocalDateTime localDateTime) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("+yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
		 String formattedLDT = localDateTime.format(formatter).replace("+", "");
		 return formattedLDT;
	}

}
