package com.kissair.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.hibernate.HibDAOFactory;
import com.kissair.dao.jdbc.JdbcDAOFactory;

/**
 * A factory for creating DAO objects.
 */
public abstract class DAOFactory {
    
    /** The Constant properties. */
    private static final Properties properties;
    
    /** The Constant SOURCE_KEY. */
    private static final String SOURCE_KEY = "datasource";
    
    /** The Constant HIB_KEY. */
    private static final String HIB_KEY = "hibernate";
    
    /** The Constant JDBC_KEY. */
    private static final String JDBC_KEY = "jdbc";
    
    /** The Constant PATH_TO_FILE. */
    private static final String PATH_TO_FILE 
    	= "src/main/resources/datasource.properties";

    /** The Constant ERROR_MSG. */
    private static final String ERROR_MSG = "Cannot create DAO. Invalid or "
    	+ "missing" + SOURCE_KEY + " property in config file";
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /** The factory instance. */
    private static DAOFactory factory;
    
    static {
	properties = new Properties();
	
	try (FileInputStream in = new FileInputStream(
		PATH_TO_FILE)) {
	    properties.load(in);
	    in.close();
	    
	    Object sourceProperty = properties.get(SOURCE_KEY);
	    
	    if (sourceProperty == null) {
		LOGGER.error(ERROR_MSG);
	    } else {
		switch (sourceProperty.toString()) {
		case HIB_KEY:
		    factory = new HibDAOFactory();
		    break;
		case JDBC_KEY:
		    factory = new JdbcDAOFactory();
		    break;
		default:
		    LOGGER.error(ERROR_MSG);
		}
	    }
	} catch (IOException e) {
	    LOGGER.error(e);
	}
    }
    
    public DAOFactory() {}
    
    /**
     * Creates a new Aircraft DAO object.
     *
     * @return the Aircraft DAO
     */
    public abstract AircraftDAO createAircraftDAO();
    
    /**
     * Creates a new Airport DAO object.
     *
     * @return the Airport DAO
     */
    public abstract AirportDAO createAirportDAO();
    
    /**
     * Creates a new Employee DAO object.
     *
     * @return the Employee DAO
     */
    public abstract EmployeeDAO createEmployeeDAO();
    
    /**
     * Creates a new Flight DAO object.
     *
     * @return the Flight DAO
     */
    public abstract FlightDAO createFlightDAO();
    
    /**
     * Gets the Aircraft DAO.
     *
     * @return the Aircraft DAO
     */
    public static AircraftDAO getAircraftDAO() {
	return factory.createAircraftDAO();
    }
    
    /**
     * Gets the Airport DAO.
     *
     * @return the Airport DAO
     */
    public static AirportDAO getAirportDAO() {
	return factory.createAirportDAO();
    }
    
    /**
     * Gets the Employee DAO.
     *
     * @return the Employee DAO
     */
    public static EmployeeDAO getEmployeeDAO() {
	return factory.createEmployeeDAO();
    }
    
    /**
     * Gets the Flight DAO.
     *
     * @return the Flight DAO
     */
    public static FlightDAO getFlightDAO() {
	return factory.createFlightDAO();
    }
}
