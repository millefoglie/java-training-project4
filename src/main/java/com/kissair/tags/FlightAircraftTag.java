package com.kissair.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.logging.log4j.LogManager;

import com.kissair.model.Aircraft;
import com.kissair.model.Flight;
import com.kissair.util.FormatHelper;

/**
 * The FlightAircraftTag class, whose purpose is to format aircraft info
 * for usage in jsp pages.
 */
public class FlightAircraftTag extends SimpleTagSupport {
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
	Aircraft aircraft = flight.getAircraft();
	FormatHelper h = FormatHelper.getInstance();
	
	out.write(h.formatAircraft(aircraft));
    }
}
