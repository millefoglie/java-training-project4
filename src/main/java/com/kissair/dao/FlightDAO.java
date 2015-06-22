package com.kissair.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.kissair.model.Employee;
import com.kissair.model.Flight;

/**
 * The Flight DAO interface.
 */
public interface FlightDAO extends DAO<Flight>{

    /**
     * Load next flights from the data source starting from now up to a
     * specified time moment.
     *
     * @param time the time span
     * @param unit the unit of time
     * @return the list of flights
     */
    List<Flight> findNextFlights(long time, TimeUnit unit);
    
    /**
     * Load all employees (the crew) for a flight.
     *
     * @param flight the flight
     * @return the set of employees
     */
    Set<Employee> findCrewForFlight(Flight flight);
    
    /**
     * Load all employees (the crew) for a flight.
     *
     * @param id the flight id
     * @return the set of employees
     */
    Set<Employee> findCrewForFlight(int id);
}
