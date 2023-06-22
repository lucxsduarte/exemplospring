package br.com.trier.springmatutino.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		return LocalDate.parse(dateStr, formatoBR).atStartOfDay(ZoneId.systemDefault());
	}
	
	public static String zoneDateTimeToStr(ZonedDateTime date) {
		return formatoBR.format(date);
	}
}
