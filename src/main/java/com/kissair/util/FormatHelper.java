package com.kissair.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.kissair.model.Aircraft;
import com.kissair.model.Airport;

/**
 * The FormatHelper class, which contains methods for proper formatting
 * of {@code Flight}'s fields data.
 */
public class FormatHelper {
    
    /** The instance of the {@code FormatHelper} class. */
    private static FormatHelper instance = new FormatHelper();
    
    /**
     * Instantiates a new format helper.
     */
    private FormatHelper() {}

    /**
     * Gets the single instance of FormatHelper.
     *
     * @return single instance of FormatHelper
     */
    public static FormatHelper getInstance() {
	return instance;
    }
    
    /**
     * Format an airport using the pattern "City, Name (IATA/FAA)".
     *
     * @param airport the airport
     * @return the formatted string (empty if {@code airport} is {@code null})
     */
    public String formatAirport(Airport airport) {
	if (airport == null) {
	    return "";
	}
	
	return String.format("%s, %s (%s)", airport.getCity(),
		airport.getName(), airport.getIataFaa());
    }
    
    /**
     * Format an aircraft using the pattern "Manufacturer, Model".
     *
     * @param aircraft the aircraft
     * @return the formatted string (empty if {@code airport} is {@code null})
     */
    public String formatAircraft(Aircraft aircraft) {
	if (aircraft == null) {
	    return "";
	}
	
	return String.format("%s %s", aircraft.getManufacturer(),
		aircraft.getModel());
    }
    
    /**
     * Format a date using the default locale short pattern.
     *
     * @param date the date
     * @param locale the locale
     * @return the formatted string (empty if {@code date} is {@code null})
     */
    public String formatDate(Date date, Locale locale) {
	if (date == null) {
	    return "";
	}
	
	if (locale == null) {
	    locale = Locale.getDefault();
	}
	
	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
		DateFormat.SHORT, locale);
	
	return df.format(date);
    }
}
