package com.example.lightsaver.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Date utils
 */
public final class DateUtils {

    private DateUtils(){
    }
    
    public static String parseCreatedAtDate(String dateString) {
        String fmt = DateTimeFormat.patternForStyle("SS", Locale.getDefault());
        DateTime dateTime = new DateTime(dateString);
        dateTime.toLocalDateTime();
        return dateTime.toLocalDateTime().toString(fmt);
    }

    public static String generateCreatedAtDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        return dateFormat.format(new Date());
    }
}