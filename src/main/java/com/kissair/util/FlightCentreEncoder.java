package com.kissair.util;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.commands.CommandName;
import com.kissair.model.Aircraft;
import com.kissair.model.Airport;
import com.kissair.model.Employee;
import com.kissair.model.Flight;

/**
 * The class for encoding messages sent via the {@code FlightCentre} web-socket.
 */
public class FlightCentreEncoder implements Encoder.Text<Message>{
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void destroy() {
	LOGGER.info("FlightCentreEncoder destroyed");
    }

    @Override
    public void init(EndpointConfig config) {
	LOGGER.info("FlightCentreEncoder initiated");
    }


    @Override
    public String encode(Message msg) throws EncodeException {
	String text = msg.getText();
	Object content = msg.getContent();

	CommandName commandKey = CommandName.fromString(msg.getText());
	
	switch (commandKey) {
	case SEND_AIRCRAFT:
	    if (content instanceof Aircraft) {
		return stringifyAircraft(text, (Aircraft) content);
	    }
	case SEND_AIRPORT:
	    if (content instanceof Airport) {
		return stringifyAirport(text, (Airport) content);
	    }
	case SEND_EMPLOYEE:
	    if (content instanceof Employee) {
		return stringifyEmployee(text, (Employee) content);
	    }
	case SEND_EMPLOYEE_FOR_FLIGHT:
	    if (content instanceof Employee) {
		return stringifyEmployeeForFlight(text, (Employee) content);
	    }
	case SEND_FLIGHT:
	    if (content instanceof Flight) {
		return stringifyFlight(text, (Flight) content);
	    }
	default:
	    LOGGER.warn("Could not encode content " + content + " for"
		    + " message " + text);
	    return null;
	}
    }

    /**
     * Stringify a message having an aircraft as its content.
     *
     * @param text the message text
     * @param aircraft the aircraft
     * @return the JSON string for the message
     */
    private String stringifyAircraft(String text, Aircraft aircraft) {
	JsonObject aircraftJsonObj = Json.createObjectBuilder()
		.add("_id", aircraft.getId())
		.add("manufacturer", aircraft.getManufacturer())
		.add("model", aircraft.getModel())
		.add("regName", aircraft.getRegName())
		.add("cabinCapacity", aircraft.getCabinCapacity())
		.build();

	JsonObject obj = Json.createObjectBuilder()
		.add("msg", text)
		.add("aircraft", aircraftJsonObj)
		.build();

	return obj.toString();
    }

    /**
     * Stringify a message having an airport as its content.
     *
     * @param text the message text
     * @param airport the airport
     * @return the JSON string for the message
     */
    private String stringifyAirport(String text, Airport airport) {
	JsonObject airportJsonObj = Json.createObjectBuilder()
		.add("_id", airport.getId())
		.add("name", airport.getName())
		.add("city", airport.getCity())
		.add("country", airport.getCountry())
		.add("iataFaa", airport.getIataFaa())
		.build();

	JsonObject obj = Json.createObjectBuilder()
		.add("msg", text)
		.add("airport", airportJsonObj)
		.build();

	return obj.toString();
    }

    /**
     * Stringify a message having an employee as its content.
     *
     * @param text the message text
     * @param employee the employee
     * @return the JSON string for the message
     */
    private String stringifyEmployee(String text, Employee employee) {
	JsonObject employeeJsonObj = Json.createObjectBuilder()
		.add("_id", employee.getId())
		.add("name", employee.getName())
		.add("surname", employee.getSurname())
		.add("regCode", employee.getRegCode())
		.add("position", employee.getPosition().getName())
		.build();

	JsonObject obj = Json.createObjectBuilder()
		.add("msg", text)
		.add("employee", employeeJsonObj)
		.build();

	return obj.toString();
    }

    /**
     * Stringify a message having an employee assigned for a flight.
     *
     * @param text the message text
     * @param employee the employee
     * @return the JSON string for the message
     */
    private String stringifyEmployeeForFlight(String text, Employee employee) {
	JsonObject employeeJsonObj = Json.createObjectBuilder()
		.add("_id", employee.getId())
		.build();

	JsonObject obj = Json.createObjectBuilder()
		.add("msg", text)
		.add("employee", employeeJsonObj)
		.build();

	return obj.toString();
    }

    /**
     * Stringify a message having a flight as its content.
     *
     * @param text the message text
     * @param flight the flight
     * @return the JSON string for the message
     */
    private String stringifyFlight(String text, Flight flight) {
	Airport depAirport = flight.getDepAirport();
	Airport arrAirport = flight.getArrAirport();
	Aircraft aircraft= flight.getAircraft();
	Map<String, Integer> crewCountMap = flight.getCrewCountMap();

	JsonObject depAirportJsonObj = Json.createObjectBuilder()
		.add("_id", depAirport.getId())
		.add("name", depAirport.getName())
		.add("city", depAirport.getCity())
		.add("country", depAirport.getCountry())
		.add("iataFaa", depAirport.getIataFaa())
		.build();

	JsonObject  arrAirportJsonObj = Json.createObjectBuilder()
		.add("_id", arrAirport.getId())
		.add("name", arrAirport.getName())
		.add("city", arrAirport.getCity())
		.add("country", arrAirport.getCountry())
		.add("iataFaa", arrAirport.getIataFaa())
		.build();

	JsonObject  aircraftJsonObj = Json.createObjectBuilder()
		.add("_id", aircraft.getId())
		.add("manufacturer", aircraft.getManufacturer())
		.add("model", aircraft.getModel())
		.add("regName", aircraft.getRegName())
		.add("cabinCapacity", aircraft.getCabinCapacity())
		.build();

	JsonArray crewJsonArray = mapToJsonArray(crewCountMap);

	JsonObject flightJsonObj = Json.createObjectBuilder()
		.add("_id", flight.getId())
		.add("depAirport", depAirportJsonObj)
		.add("arrAirport", arrAirportJsonObj)
		.add("depTimestamp", flight.getDepTimestamp().toString())
		.add("arrTimestamp", flight.getArrTimestamp().toString())
		.add("aircraft", aircraftJsonObj)
		.add("crewCountMap", crewJsonArray)
		.build();

	JsonObject  obj = Json.createObjectBuilder()
		.add("msg", text)
		.add("flight", flightJsonObj)
		.build();

	return obj.toString();
    }

    /**
     * Convert a map with {@code String} keys and {@Object} values 
     * to a JSON array. Each value is converted to a {@code String} by
     * the {@code toString()} method.
     *
     * @param map the map
     * @return the json array
     */
    private JsonArray mapToJsonArray(Map<String, ? extends Object> map) {
	JsonArrayBuilder builder = Json.createArrayBuilder();

	for (String s : map.keySet()) {
	    builder.add(Json.createObjectBuilder()
		    .add("key", s)
		    .add("value", map.get(s).toString()));
	}

	return builder.build();
    }
}
