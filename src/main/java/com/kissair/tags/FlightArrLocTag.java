package com.kissair.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.logging.log4j.LogManager;

import com.kissair.model.Airport;
import com.kissair.model.Flight;
import com.kissair.util.FormatHelper;

/**
 * The FlightArrLocTag class, whose purpose is to format flight arrival
 * location info for usage in jsp.
 */
public class FlightArrLocTag extends SimpleTagSupport {
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
	Airport airport = flight.getArrAirport();
	FormatHelper h = FormatHelper.getInstance();
	
	out.write(h.formatAirport(airport));
    }
}
