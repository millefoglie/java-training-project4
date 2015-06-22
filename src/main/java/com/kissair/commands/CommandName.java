package com.kissair.commands;

/**
 * The supported commands names.
 */
public enum CommandName {
    GET_AIRCRAFTS("getAircrafts"),
    GET_AIRPORTS("getAirports"),
    GET_EMPLOYEES("getEmployees"),
    GET_EMPLOYEES_FOR_FLIGHT("getEmployeesForFlight"),
    GET_FLIGHTS("getFlights"),
    SEND_AIRCRAFT("sendAircraft"),
    SEND_AIRPORT("sendAirport"),
    SEND_EMPLOYEE("sendEmployee"),
    SEND_EMPLOYEE_FOR_FLIGHT("sendEmployeeForFlight"),
    SEND_FLIGHT("sendFlight"),
    SEND_AIRCRAFTS("sendAircrafts"),
    SEND_AIRPORTS("sendAirports"),
    SEND_EMPLOYEES("sendEmployees"),
    SEND_EMPLOYEES_FOR_FLIGHT("sendEmployeesForFlight"),
    SEND_FLIGHTS("sendFlights"),
    PROCESS_FLIGHT("processFlight"),
    PROCESS_EMPLOYEES_FOR_FLIGHT("processEmployeesForFlight"),
    BROADCAST("broadcast");

    /** The actual command name. */
    private String commandText;

    /**
     * Instantiates a new command name.
     *
     * @param commandText the command text
     */
    private CommandName(String commandText) {
	this.commandText = commandText;
    }

    @Override
    public String toString() {
	return commandText;
    }

    /**
     * Gets the command name from a string.
     *
     * @param commandText the actual command name
     * @return the command name
     */
    public static CommandName fromString(String commandText) {
	if (commandText != null) {
	    for (CommandName name : CommandName.values()) {
		if (commandText.equalsIgnoreCase(name.commandText)) {
		    return name;
		}
	    }
	}
	
	throw new IllegalArgumentException(
		"No constant with text " + commandText + " found");
    }
}
