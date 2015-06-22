package com.kissair.tags;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.logging.log4j.LogManager;

import com.kissair.model.Flight;
import com.kissair.util.FormatHelper;

/**
 * The FlightDepTimestampTag class, whose purpose is to format flight departure
 * date and time for usage in jsp.
 */
public class FlightDepTimestampTag extends SimpleTagSupport {
    private Flight flight;
    
    /**
     * Sets the flight.
     *
     * @param flight the new flight
     */
    public void setFlight(Flight flight) {
	this.flight = flight;
    }

    @Override
    public void doTag() throws JspException, IOException {
	if (flight == null) {
	    LogManager.getLogger().warn("Tried to use FlightAircraftTag"
	    	+ " with no Flight object provided.");
	    return;
	}
	
	JspWriter out = getJspContext().getOut();
	Date date = flight.getDepTimestamp();
	FormatHelper h = FormatHelper.getInstance();
	
	HttpSession session = ((PageContext) getJspContext()).getSession();
	Locale locale = (Locale) session.getAttribute("locale");
	
	out.write(h.formatDate(date, locale));
    }
}
