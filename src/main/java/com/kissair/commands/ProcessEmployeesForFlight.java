package com.kissair.commands;

import java.util.Set;

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
 * The ProcessEmployeesForFlight Command. Processes a message containing info
 * on employees assigned for a flight.
 */
public class ProcessEmployeesForFlight implements Command {
    
    /** The logger. */
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("unchecked")
     @RolesAllowed("dispatcher")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session,
	    Message msg) {
	// content should consist of flightId (1) and set of employees (2)
	Object[] content = (Object[]) msg.getContent();
	
	if ((content == null) || !(content[0] instanceof Integer)
		|| !(content[1] instanceof Set<?>)) {
	    LOGGER.warn("Illegal content for " + msg.getText() + " message");
	    return;
	}
	
	FlightDAO flightDao = DAOFactory.getFlightDAO();
	Flight flight = flightDao.findById((Integer) content[0]);
	Set<Employee> crew = (Set<Employee>) content[1];
	FlightCentreSE centre =
		(FlightCentreSE) session.getUserProperties()
		.get(FlightCentreSE.WS_KEY);

	flight.setCrew(crew);
	flightDao.update(flight);
	
	if (centre != null) {
	    Message  message = 
		    new Message(CommandName.SEND_FLIGHT.toString(), flight);
	    
	    CommandFactory.getCommand(CommandName.BROADCAST)
	    	.execute(endpointConfig, session, message);
	} else {
	    LOGGER.warn("No ServerEndpoint associated with the session");
	}
    }
}
