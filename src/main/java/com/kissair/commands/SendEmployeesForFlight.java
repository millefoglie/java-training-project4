package com.kissair.commands;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.DAOFactory;
import com.kissair.dao.FlightDAO;
import com.kissair.model.Employee;
import com.kissair.model.Flight;
import com.kissair.util.Message;

/**
 * The SendEmployeesForFlight Command. Sends a message with info on employees
 * assigned for a flight to the client.
 */
public class SendEmployeesForFlight implements Command {
    
    /** The logger. */
    private static final Logger LOGGER = LogManager.getLogger();

    @RolesAllowed("dispatcher")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session,
	    Message msg) {
	if (!(msg.getContent() instanceof Integer)) {
	    LOGGER.warn("Invalid content in " + msg.getText() + " message");
	    return;
	}
	
	int flightId = (Integer) msg.getContent();

	if (flightId < 1) {
	    LOGGER.warn("Invalid flightId.");
	    return;
	}

	FlightDAO dao = DAOFactory.getFlightDAO();
	Flight flight = dao.findById(flightId);
	Set<Employee> crew = flight.getCrew();
	Message message = null;

	for (Employee e : crew) {
	    message = new Message(CommandName.SEND_EMPLOYEE_FOR_FLIGHT, e);
	    session.getAsyncRemote().sendObject(message);
	}
    }
}
