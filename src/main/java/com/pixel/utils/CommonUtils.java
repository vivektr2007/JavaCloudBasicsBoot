package com.pixel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

	public static String thumbUrl(String str, String addPart) {

		if (addPart == null) {
			return null;
		}
		String s = new String(str);
		String ext = s.substring(s.lastIndexOf("."));
		String ext1 = s.substring(0, s.lastIndexOf("."));
		String s1 = ext1 +"_"+addPart + ext;
		return s1;
	}

	public static String generateFileNameForS3(String fileName){
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		String dateStr = sdf.format(date);
		dateStr = dateStr.replaceAll("\\:", "_");
		dateStr = dateStr.replaceAll("\\-", "");
		dateStr = dateStr.replaceAll("\\.", "_");
		
		fileName = fileName.substring(0,fileName.lastIndexOf(".")) + dateStr + fileName.substring(fileName.lastIndexOf("."));
		
		return fileName;
	}
	
	public static String getDisplayDate(Date date){
		String displayDate = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
		displayDate = sdf.format(date);
		
		return displayDate;
	}
	
	public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
	
}
