package com.kissair.dao.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.AircraftDAO;
import com.kissair.dao.AirportDAO;
import com.kissair.dao.DAOFactory;
import com.kissair.dao.EmployeeDAO;
import com.kissair.dao.FlightDAO;

/**
 * A factory for creating JDBC DAO objects.
 */
public class JdbcDAOFactory extends DAOFactory {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private InitialContext initial;
    private DataSource ds;

    /**
     * Instantiates a new jdbc dao factory.
     */
    public JdbcDAOFactory() {
	try {
	    initial = new InitialContext();
	    ds = (DataSource) initial.lookup("jdbc/MysqlHikari");
	} catch (NamingException e) {
	    LOGGER.error(e);
	}
    }

    @Override
    public AircraftDAO createAircraftDAO() {
	return new AircraftJdbcDAO(ds);
    }

    @Override
    public AirportDAO createAirportDAO() {
	return new AirportJdbcDAO(ds);
    }

    @Override
    public EmployeeDAO createEmployeeDAO() {
	return new EmployeeJdbcDAO(ds);
    }

    @Override
    public FlightDAO createFlightDAO() {
	return new FlightJdbcDAO(ds);
    }
}
