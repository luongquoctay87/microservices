package com.microservice.commentservice.api.validate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
    public static String convertDateToString(Date _date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = simpleDateFormat.format(_date);
        return date;
    }
    public static Date dateNew(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        try {
            Date date =simpleDateFormat.parse(simpleDateFormat.format(new Date())) ;
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

}
