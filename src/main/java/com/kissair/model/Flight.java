package com.kissair.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Flight class.
 */
public class Flight implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3479063845951145020L;
    
    /** The id. */
    private int id;
    
    /** The departure airport. */
    private Airport depAirport;
    
    /** The arrival airport. */
    private Airport arrAirport;
    
    /** The departure timestamp. */
    private Timestamp depTimestamp;
    
    /** The arrival timestamp. */
    private Timestamp arrTimestamp;
    
    /** The aircraft. */
    private Aircraft aircraft;
    
    /** The crew. */
    private Set<Employee> crew;
    
    /**
     * Instantiates a new flight.
     */
    public Flight() {}

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the departure airport.
     *
     * @return the departure airport
     */
    public Airport getDepAirport() {
        return depAirport;
    }

    /**
     * Sets the departure airport.
     *
     * @param depAirport the new departure airport
     */
    public void setDepAirport(Airport depAirport) {
        this.depAirport = depAirport;
    }

    /**
     * Gets the arrival airport.
     *
     * @return the arrival airport
     */
    public Airport getArrAirport() {
        return arrAirport;
    }

    /**
     * Sets the arrival airport.
     *
     * @param arrAirport the new arrival airport
     */
    public void setArrAirport(Airport arrAirport) {
        this.arrAirport = arrAirport;
    }

    /**
     * Gets the departure timestamp.
     *
     * @return the departure timestamp
     */
    public Timestamp getDepTimestamp() {
        return depTimestamp;
    }

    /**
     * Sets the departure timestamp.
     *
     * @param depTimestamp the new departure timestamp
     */
    public void setDepTimestamp(Timestamp depTimestamp) {
        this.depTimestamp = depTimestamp;
    }

    /**
     * Gets the arrival timestamp.
     *
     * @return the arrival timestamp
     */
    public Timestamp getArrTimestamp() {
        return arrTimestamp;
    }

    /**
     * Sets the arrival timestamp.
     *
     * @param arrTimestamp the new arrival timestamp
     */
    public void setArrTimestamp(Timestamp arrTimestamp) {
        this.arrTimestamp = arrTimestamp;
    }

    /**
     * Gets the aircraft.
     *
     * @return the aircraft
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     * Sets the aircraft.
     *
     * @param aircraft the new aircraft
     */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * Gets the crew.
     *
     * @return the crew
     */
    public Set<Employee> getCrew() {
        return crew;
    }

    /**
     * Sets the crew.
     *
     * @param crew the new crew
     */
    public void setCrew(Set<Employee> crew) {
        this.crew = crew;
    }
    
    /**
     * Gets the map that gives numbers of employees for the flight 
     * of each position.
     *
     * @return the crew count map
     */
    public Map<String, Integer> getCrewCountMap() {
	Map<String, Integer> result = new HashMap<>();
	String positionName;
	
	for (Employee emp : crew) {
	    positionName = emp.getPosition().getName();
	    
	    if (result.containsKey(positionName)) {
		result.put(positionName, result.get(positionName) + 1);
	    } else {
		result.put(positionName, 1);
	    }
	}
	
	return result;
    }
    
    /**
     * Check if the flight parameters are valid.
     *
     * @return true, if successful
     */
    public boolean validate() {
	return (depAirport != null) 
		&& (arrAirport != null)
		&& (aircraft != null) 
		&& (arrTimestamp.getTime() - depTimestamp.getTime() > 0);
    }
}
