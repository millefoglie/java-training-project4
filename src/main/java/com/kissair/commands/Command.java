package com.kissair.commands;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import com.kissair.util.Message;

/**
 * The FlightCentre web-socket Command interface.
 */
@FunctionalInterface
public interface Command {
    
    /**
     * Execute the command.
     *
     * @param endpointConfig the endpoint config
     * @param session the session
     * @param msg the message
     */
    void execute(EndpointConfig endpointConfig, Session session, Message msg);
}
