package com.kissair.commands;

import java.util.HashSet;

import javax.annotation.security.RolesAllowed;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.controllers.FlightCentreSE;
import com.kissair.dao.DAOFactory;
import com.kissair.dao.FlightDAO;
import com.kissair.model.Employee;
import com.kissair.model.Flight;
import com.kissair.util.Message;

/**
 * The ProcessFlight Command. Processes a message with flight info and creates
 * a new flight or updates an existing one. 
 */
public class ProcessFlight implements Command {
    
    /** The logger. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    @RolesAllowed("admin")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session,
	    Message msg) {
	Flight flight = (Flight) msg.getContent();

	if (flight == null) {
	    LOGGER.warn("Invalid content in " + msg.getText() + " message");
	    return;
	}
	
	if (!flight.validate()) {
	    LOGGER.warn("Invalid flight properties.");
	    return;
	}
	
	FlightDAO dao = DAOFactory.getFlightDAO();
	FlightCentreSE centre = (FlightCentreSE) session.getUserProperties()
		.get(FlightCentreSE.WS_KEY);
	
	if (flight.getId() > 0) {
	    flight.setCrew(dao.findCrewForFlight(flight));
	    dao.update(flight);
	} else {
	    flight.setCrew(new HashSet<Employee>());
	    dao.add(flight);
	}
	
	if (centre != null) {
	    Message  message = new Message(CommandName.SEND_FLIGHT, flight);
	    
	    CommandFactory.getCommand(CommandName.BROADCAST)
	    	.execute(endpointConfig, session, message);
	} else {
	    LOGGER.warn("No ServerEndpoint associated with the session");
	}
    }
}
