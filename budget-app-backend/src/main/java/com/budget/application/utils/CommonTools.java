package com.budget.application.utils;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;


@Service
public class CommonTools {

	public LocalDateTime getLocalDateTimeFromISODate(String isoDate) {
		LocalDateTime finalDate = null;
		try {
			Timestamp timestampFromIsoDate = getTimeStampFromISODate(isoDate);
			finalDate = timestampFromIsoDate.toLocalDateTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return finalDate;
	}
	

	public Timestamp getTimeStampFromISODate(String isoDate) throws Exception {
		Timestamp result = null;
		try {
			ZonedDateTime date = ZonedDateTime.parse(isoDate);
			LocalDateTime ldt = date.toLocalDateTime();
			result = Timestamp.valueOf(ldt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			throw new Exception(
					"Error during converstion between ISO Date String and TimeStamp. Check if entry data is in proper ISO String format");
		}

		return result;
	}

	/**
	 * Returns currentDate as LocalDateTime class object
	 * 
	 * @return
	 */
	public LocalDateTime getCurrentDate() {
		return LocalDateTime.now();
	}
}
