package com.kissair.util;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.commands.CommandName;
import com.kissair.dao.AircraftDAO;
import com.kissair.dao.AirportDAO;
import com.kissair.dao.DAOFactory;
import com.kissair.dao.EmployeeDAO;
import com.kissair.model.Employee;
import com.kissair.model.Flight;

/**
 * The class for decoding messages received from the {@code FlightCentre}
 * clients.
 */
public class FlightCentreDecoder implements Decoder.Text<Message> {
    
    /** The logger. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /** The message text field key. */
    private static final String MSG_KEY = "msg";
    
    @Override
    public void destroy() {
	LOGGER.info("FlightCentreDecoder was destroyed.");
    }

    @Override
    public void init(EndpointConfig arg0) {
	LOGGER.info("FlightCentreDecoder was initiated.");
    }

    @Override
    public Message decode(String s) throws DecodeException {
	JsonReader reader = Json.createReader(new StringReader(s));
	JsonObject obj = null;
	
	try {
	    obj = reader.readObject();
	} catch (JsonException e) {
	    LOGGER.error("Could not decode JSON string " + s 
		    + ". " + e);
	    return new Message();
	}
	
	String commandKey = obj.getString(MSG_KEY, null);
	
	switch (CommandName.fromString(commandKey)) {
	case GET_AIRCRAFTS:
	case GET_AIRPORTS:
	case GET_EMPLOYEES:
	case GET_FLIGHTS:
	    return new Message(commandKey, null);
	case GET_EMPLOYEES_FOR_FLIGHT:
	    return decodeGetEmployeesForFlight(obj);
	case SEND_FLIGHT:
	    return decodeSendFlight(obj);
	case SEND_EMPLOYEES_FOR_FLIGHT:
	    return decodeSendEmployeesForFlight(obj);
	default:
	    LOGGER.warn("Could not decode message " + s);
	    return new Message();
	}
    }

    @Override
    public boolean willDecode(String s) {
	// if JSON message cannot be parsed, empty message will be returned
	return true;
    }
    
    /**
     * Decode the employees assigned for a flight message from the JSON object.
     *
     * @param obj the JSON object
     * @return the message
     */
    private Message decodeGetEmployeesForFlight(JsonObject obj) {
	Integer flightId = obj.getInt("flightId", -1);
	
	return new Message(CommandName.GET_EMPLOYEES_FOR_FLIGHT, flightId);
    }
    
    /**
     * Decode the send flight message form the JSON object.
     *
     * @param obj the JSON object
     * @return the message
     */
    private Message decodeSendFlight(JsonObject obj) {
	JsonObject flightObj = obj.getJsonObject("flight");
	
	if (flightObj == null) {
	    LOGGER.warn("SendFlight message contained no Flight object.");
	    return new Message();
	}
	
	Flight flight = new Flight();
	
	int id = flightObj.getInt("_id", -1);
	int depAirportId = flightObj.getInt("depAirportId", -1);
	int arrAirportId = flightObj.getInt("arrAirportId", -1);
	int aircraftId = flightObj.getInt("aircraftId", -1);
	
	Timestamp depTimestamp = null;
	Timestamp arrTimestamp = null;

	flight.setId(id > 0 ? id : -1);
	
	depTimestamp = Timestamp.valueOf(
		flightObj.getString("depTimestamp", ""));
	arrTimestamp = Timestamp.valueOf(
		flightObj.getString("arrTimestamp", ""));

	AircraftDAO aircraftDao = DAOFactory.getAircraftDAO();
	AirportDAO airportDao = DAOFactory.getAirportDAO();

	// if ids are negative, will return nulls
	flight.setDepAirport(airportDao.findById(depAirportId));
	flight.setArrAirport(airportDao.findById(arrAirportId));
	flight.setDepTimestamp(depTimestamp);
	flight.setArrTimestamp(arrTimestamp);
	flight.setAircraft(aircraftDao.findById(aircraftId));
	
	return new Message(CommandName.SEND_FLIGHT, flight);
    }
    
    /**
     * Decode the send employees for flight message from the JSON object.
     *
     * @param obj the JSON object
     * @return the message
     */
    private Message decodeSendEmployeesForFlight(JsonObject obj) {
	JsonArray crew = obj.getJsonArray("crew");
	int flightId = obj.getInt("flightId", -1);
	
	if ((flightId < 1) || (crew == null)) {
	    LOGGER.warn("SendEmployeesForFlight message did not contain"
	    	+ " valid flightId or crew array.");
	    return new Message();
	}
	
	Object[] result = new Object[2];
	Set<Employee> crewSet = new HashSet<>();
	EmployeeDAO employeeDao = DAOFactory.getEmployeeDAO();
	
	crew.forEach(e -> crewSet.add(
		employeeDao.findById(((JsonObject) e).getInt("_id"))
		));
	
	result[0] = flightId;
	result[1] = crewSet;
	
	return new Message(CommandName.SEND_EMPLOYEES_FOR_FLIGHT, result);
    }
}
