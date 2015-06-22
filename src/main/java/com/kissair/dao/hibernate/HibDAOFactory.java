package com.kissair.dao.hibernate;

import com.kissair.dao.AircraftDAO;
import com.kissair.dao.AirportDAO;
import com.kissair.dao.DAOFactory;
import com.kissair.dao.EmployeeDAO;
import com.kissair.dao.FlightDAO;

/**
 * A factory for creating Hibernate DAO objects.
 */
public class HibDAOFactory extends DAOFactory {

    /**
     * Instantiates a new Hibernate DAO factory.
     */
    public HibDAOFactory() {}

    @Override
    public AircraftDAO createAircraftDAO() {
	return new AircraftHibDAO();
    }

    @Override
    public AirportDAO createAirportDAO() {
	return new AirportHibDAO();
    }

    @Override
    public EmployeeDAO createEmployeeDAO() {
	return new EmployeeHibDAO();
    }

    @Override
    public FlightDAO createFlightDAO() {
	return new FlightHibDAO();
    }
}
