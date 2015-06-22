package com.kissair.util;

import com.kissair.commands.CommandName;

/**
 * This class encapsulates messages transmitted between the
 * {@code FlightCentre} server endpoint and its clients.
 */
public class Message {
    
    /** The message text (e.g. the command).*/
    private String text;
    
    /** The message content.*/
    private Object content;

    /**
     * Instantiates a new message.
     */
    public Message() {
    }
    
    /**
     * Instantiates a new message.
     *
     * @param text the message text
     * @param content the message content
     */
    public Message(String text, Object content) {
	this.text = text;
	this.content = content;
    }
    
    /**
     * Instantiates a new message.
     *
     * @param name the command name
     * @param content the message content
     */
    public Message(CommandName name, Object content) {
	this.text = name.toString();
	this.content = content;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getText() {
	return text;
    }

    /**
     * Gets the message content.
     *
     * @return the message content
     */
    public Object getContent() {
	return content;
    }
}
