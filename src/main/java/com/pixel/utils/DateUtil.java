package com.pixel.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {

	public static String getNextRundate(String runDate, String interval) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = null;
		try {
			date = sdf.parse(runDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (interval.equalsIgnoreCase("daily")) {
			cal.add(Calendar.DATE, 1);
		} else if (interval.equalsIgnoreCase("weekly")) {
			cal.add(Calendar.DATE, 7);
		} else if (interval.equalsIgnoreCase("monthly")) {
			cal.add(Calendar.MONTH, 1);
		} else if (interval.equalsIgnoreCase("yearly")) {
			cal.add(Calendar.YEAR, 1);
		}

		return sdf1.format(cal.getTime());
	}

	public static String dateToStringDDMMYYYY(Date date) {
		String dateStr = "";
		try {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dateStr = sdf.format(date);
			DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = sdf1.format(sdf.parse(dateStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	
	private static LinkedHashMap<String, Integer> getStrToIntMonthMap() {

		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("January", 0);
		map.put("February", 1);
		map.put("March", 2);
		map.put("April", 3);
		map.put("May", 4);
		map.put("June", 5);
		map.put("July", 6);
		map.put("August", 7);
		map.put("September", 8);
		map.put("October", 9);
		map.put("November", 10);
		map.put("December", 11);

		return map;
	}

	private static LinkedHashMap<Integer, String> getIntToStrMonthMap() {

		LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
		map.put(0, "January");
		map.put(1, "February");
		map.put(2, "March");
		map.put(3, "April");
		map.put(4, "May");
		map.put(5, "June");
		map.put(6, "July");
		map.put(7, "August");
		map.put(8, "September");
		map.put(9, "October");
		map.put(10, "November");
		map.put(11, "December");

		return map;
	}
	
	public static Map<String, List<String>> getYearAndMonthFromSession(String fromDate, String toDate) {

		List<String> monthList = new ArrayList<>();
		List<String> yearList = new ArrayList<>();

		Map<String, Integer> strToIntMonthMap = getStrToIntMonthMap();

		Map<String, List<String>> returnMap = new HashMap<>();

		String fromdateArr[] = fromDate.split(" ");
		String todateArr[] = toDate.split(" ");

		Calendar fromDateCal = Calendar.getInstance();
		fromDateCal.set(Calendar.MONTH, strToIntMonthMap.get(fromdateArr[0]));
		fromDateCal.set(Calendar.YEAR, Integer.parseInt(fromdateArr[1]));

		Calendar toDateCal = Calendar.getInstance();
		toDateCal.set(Calendar.MONTH, strToIntMonthMap.get(todateArr[0]));
		toDateCal.set(Calendar.YEAR, Integer.parseInt(todateArr[1]));

		while (fromDateCal.compareTo(toDateCal) <= 0) {

			String month = getIntToStrMonthMap().get(fromDateCal.get(Calendar.MONTH));
			monthList.add(month);
			
			String year = String.valueOf(fromDateCal.get(Calendar.YEAR));
			if(!yearList.contains(year)) {
				yearList.add(year);
			}
			
			fromDateCal.add(Calendar.MONTH, 1);
		}

		returnMap.put("yearList", yearList);
		returnMap.put("monthList", monthList);
		
		return returnMap;
	}

	public static void main(String args[]) {
		String fromDate = "July 2018";
		String toDate = "June 2019";

		Map<String, List<String>> returnData = getYearAndMonthFromSession(fromDate, toDate);
		
		System.out.println(returnData);
		
		
	}

}
