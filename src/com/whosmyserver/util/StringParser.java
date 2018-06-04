package com.whosmyserver.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringParser {

	
	//Get date title
	public static String getDateTitle(String date){
		Calendar myCalendar = Calendar.getInstance();
		String myFormat = "MM/dd/yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
	    //edit_date1.setText(sdf.format(myCalendar.getTime()));
	    //String[] dateSplitVal = date.split("/");
	    //String date1 = dateSplitVal[0]+"/"+dateSplitVal[1]+"/"+"20"+dateSplitVal[2];
		int dayn = myCalendar.get(Calendar.DAY_OF_WEEK);
		String dateTitle = "";
		String[] dayName = {"Sun","Mon","Tues","Wed","Thurs","Fri","Sat",
				"Sun","Mon","Tues","Wed","Thurs","Fri","Sat"};
		String[] monthList = {"Jan","Feb","Mar","Apr","May","Jun","Jul",
				"Aug","Sep","Oct","Nov","Dec",""}; 
		
		int diffDays = 0;
		//get time difference
		try{
		Date dateObj1 = sdf.parse(sdf.format(myCalendar.getTime()));
		Date dateObj2 = sdf.parse(date);
		long diff = dateObj2.getTime() - dateObj1.getTime();
		diffDays =  (int) (diff / (24* 1000 * 60 * 60));
		}catch(Exception e){
			
		}
		
		if(diffDays==0){
			dateTitle = "Today";
		}else if(diffDays==1){
			dateTitle =  "Tomorrow";
		}else if(diffDays>1&&diffDays<7){
			dateTitle = dayName[dayn+diffDays];
		}else{
			String[] strDate = date.split("/");
			dateTitle = monthList[Integer.parseInt(strDate[0])-1]+", "+strDate[1]+" "+strDate[2];
		}
		
		
		return dateTitle;
	}
}
