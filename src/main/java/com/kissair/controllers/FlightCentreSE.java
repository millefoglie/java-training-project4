package com.kissair.controllers;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.commands.Command;
import com.kissair.commands.CommandFactory;
import com.kissair.commands.CommandName;
import com.kissair.util.FlightCentreDecoder;
import com.kissair.util.FlightCentreEncoder;
import com.kissair.util.Message;

/**
 * The FlightCentre Server Endpoint class.
 */
@ServerEndpoint(
	value = "/restricted/flightcentre",
	encoders = {FlightCentreEncoder.class},
	decoders = {FlightCentreDecoder.class}	
	)
public class FlightCentreSE {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant WS_KEY. */
    public static final String WS_KEY = "websocket";

    /** The endpoint config. */
    private EndpointConfig endpointConfig;

    /** The session. */
    private Session session;

    /**
     * Handler for the onOpen event.
     *
     * @param endpointConfig the endpoint config
     * @param session the session
     */
    @OnOpen
    public void onOpen(EndpointConfig endpointConfig, Session session) {
	this.endpointConfig = endpointConfig;
	this.session = session;

	session.getUserProperties().put(WS_KEY, this);
	LOGGER.info("FlightCentreSE WebSocket was opened.");
    }

    /**
     * Handler for the onClose event.
     *
     * @param reason the reason
     */
    @OnClose
    public void onClose(CloseReason reason) {
	session.getUserProperties().put(WS_KEY, null);
	LOGGER.info("FlightCentreSE WebSocket was closed: " 
		+ reason.getReasonPhrase());
    }

    /**
     * Handler for the onError event
     *
     * @param t the throwable
     */
    @OnError
    public void onError(Throwable t) {
	session.getUserProperties().put(WS_KEY, null);
	LOGGER.info("FlightCentreSE WebSocket had an error: " + t.getMessage());
    }
    
    /**
     * Handler for the onMessage event.
     *
     * @param msg the message
     */
    @OnMessage
    public void onMessage(Message msg) {
	Command cmd = CommandFactory.getResponseCommand(
		CommandName.fromString(msg.getText()));

	if (cmd != null) {
	    cmd.execute(endpointConfig, session, msg);
	}
    }
}
