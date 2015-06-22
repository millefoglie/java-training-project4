package com.kissair.commands;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.dao.AirportDAO;
import com.kissair.dao.DAOFactory;
import com.kissair.model.Airport;
import com.kissair.util.Message;

/**
 * The SendAirports Command. Sends messages with the available airports info
 * to the client.
 */
public class SendAirports implements Command {

    @RolesAllowed("admin")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session, Message msg) {
	AirportDAO dao = DAOFactory.getAirportDAO();
	List<Airport> airports = dao.findAll();
	Message message = null;
	
	for (Airport a : airports) {
	    message = new Message(CommandName.SEND_AIRPORT, a);
	    session.getAsyncRemote().sendObject(message);
	}
    }
}
