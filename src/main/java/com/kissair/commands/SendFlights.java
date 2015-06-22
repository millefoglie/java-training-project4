package com.kissair.commands;

import java.util.List;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.dao.DAOFactory;
import com.kissair.dao.FlightDAO;
import com.kissair.model.Flight;
import com.kissair.util.Message;

/**
 * The SendFlights Command. Sends messages with the available flights info
 * to the client.
 */
public class SendFlights implements Command {
    
    @Override
    public void execute(EndpointConfig endpointConfig, Session session, Message msg) {
	FlightDAO dao = DAOFactory.getFlightDAO();
	List<Flight> flights = dao.findAll();
	Message message;
	
	for (Flight f : flights) {
	    message = new Message(CommandName.SEND_FLIGHT, f);
	    session.getAsyncRemote().sendObject(message);
	}
    }
}
