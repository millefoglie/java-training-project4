package com.kissair.commands;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.util.Message;

/**
 * The Broadcast Command. Sends a message to all available clients.
 */
public class Broadcast implements Command {
    
    @Override
    public void execute(EndpointConfig endpointConfig, Session session,
	    Message msg) {
	for (Session s : session.getOpenSessions()) {
	    s.getAsyncRemote().sendObject(msg);
	}
    }
}
