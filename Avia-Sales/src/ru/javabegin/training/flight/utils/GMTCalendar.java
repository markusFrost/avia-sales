package ru.javabegin.training.flight.utils;


import java.util.Calendar;
import java.util.TimeZone;

public class GMTCalendar
{
    public static Calendar getInstance()
    {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }
}
