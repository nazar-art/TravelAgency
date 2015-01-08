package com.zasadnyy.task10.utils;

import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {
    private static Logger log = Logger.getLogger(ValidationUtils.class);

    public static boolean isNumber(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static boolean validEmail(String email) {
        return email.matches("[^ @]*@[^ @]*\\.[^ @]{2,}");
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFormat) {
        boolean result = true;
        if (dateToValidate == null) {
            result = false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateToValidate);

        } catch (ParseException e) {
            log.error(e);
            result = false;
        }

        return result;
    }

    public static boolean isDateBigger(String first, String second, String dateFormat) {
        boolean result = false;
        try {
            Date firstDate = new SimpleDateFormat(dateFormat).parse(first);
            Date secondDate = new SimpleDateFormat(dateFormat).parse(second);
            if (firstDate.after(secondDate)) {
                result = true;
            }
        } catch (ParseException e) {
            log.error(e);
        }
        return result;
    }

    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (str == null || str.isEmpty()) {
            result = true;
        }
        return result;
    }
}
