package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtils {

	public static String format(Date date,String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	public static Date format(String date,String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static String format2String(String date,String pattern){
		Date datetime = format(date, pattern);
		return format(datetime, pattern);
	}
	
	public static Date now(String pattern){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(format(date,pattern));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static Long getStartTime(String datetime){
		DateTime now = new DateTime(DateUtils.format(datetime, "yyyy-MM-dd"));
		now = now.withTime(0, 0, 0, 0);
		return now.toDate().getTime();
	}
	
	public static Long getEndTime(String datetime){
		DateTime now = new DateTime(DateUtils.format(datetime, "yyyy-MM-dd"));
		now = now.withTime(23,59,59,999);
		return now.toDate().getTime();
	}
}
