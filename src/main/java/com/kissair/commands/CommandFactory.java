package com.kissair.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating Command objects.
 */
public class CommandFactory {
    
    /** The Map of response commands for the received message commands. */
    private static Map<CommandName, CommandName> responseCommand =
	    new HashMap<CommandName, CommandName>();
    
    static {
	responseCommand.put(CommandName.GET_AIRCRAFTS,
		CommandName.SEND_AIRCRAFTS);
	responseCommand.put(CommandName.GET_AIRPORTS,
		CommandName.SEND_AIRPORTS);
	responseCommand.put(CommandName.GET_EMPLOYEES,
		CommandName.SEND_EMPLOYEES);
	responseCommand.put(CommandName.GET_EMPLOYEES_FOR_FLIGHT,
		CommandName.SEND_EMPLOYEES_FOR_FLIGHT);
	responseCommand.put(CommandName.GET_FLIGHTS,
		CommandName.SEND_FLIGHTS);
	responseCommand.put(CommandName.SEND_FLIGHT,
		CommandName.PROCESS_FLIGHT);
	responseCommand.put(CommandName.SEND_EMPLOYEES_FOR_FLIGHT,
		CommandName.PROCESS_EMPLOYEES_FOR_FLIGHT);
    }
    
    /**
     * Instantiates a new command factory.
     */
    private CommandFactory() {}
    
    /**
     * Gets the command.
     *
     * @param name the command name
     * @return the command
     */
    public static Command getCommand(CommandName name) {
	switch (name) {
	case SEND_AIRCRAFTS:
	    return new SendAircrafts();
	case SEND_AIRPORTS:
	    return new SendAirports();
	case SEND_EMPLOYEES:
	    return new SendEmployees();
	case SEND_EMPLOYEES_FOR_FLIGHT:
	    return new SendEmployeesForFlight();
	case SEND_FLIGHTS:
	    return new SendFlights();
	case PROCESS_FLIGHT:
	    return new ProcessFlight();
	case PROCESS_EMPLOYEES_FOR_FLIGHT:
	    return new ProcessEmployeesForFlight();
	case BROADCAST:
	    return new Broadcast();
	default:
	    return null;
	}
    }
    
    /**
     * Gets the response command for handling the received message.
     *
     * @param name the name of the received message command
     * @return the response command
     */
    public static Command getResponseCommand(CommandName name) {
	return getCommand(responseCommand.get(name));
    }
}
