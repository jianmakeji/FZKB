package com.jianma.fzkb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public static String strToDateLong(String strDate) {
		SimpleDateFormat sdfCST = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);  
		String formatDate = "";
		try {
			Date d = sdfCST.parse(strDate);
			formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return formatDate;
	}
	
	public static Date strToDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    try {
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    return null;
	}
}
