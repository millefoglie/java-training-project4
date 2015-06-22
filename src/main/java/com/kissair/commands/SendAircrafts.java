package com.kissair.commands;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.dao.AircraftDAO;
import com.kissair.dao.DAOFactory;
import com.kissair.model.Aircraft;
import com.kissair.util.Message;

/**
 * The SendAircrafts Command. Sends messages with available aircrafts info
 * to the client.
 */
public class SendAircrafts implements Command {

    @RolesAllowed("admin")
    @Override
    public void execute(EndpointConfig endpointConfig, Session session, Message msg) {
	AircraftDAO dao = DAOFactory.getAircraftDAO();
	List<Aircraft> aircrafts = dao.findAll();
	Message message = null;
	
	for (Aircraft a : aircrafts) {
	    message = new Message(CommandName.SEND_AIRCRAFT, a);
	    session.getAsyncRemote().sendObject(message);
	}
    }
}
