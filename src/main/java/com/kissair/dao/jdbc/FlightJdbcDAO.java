package com.kissair.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.AircraftDAO;
import com.kissair.dao.AirportDAO;
import com.kissair.dao.EmployeeDAO;
import com.kissair.dao.FlightDAO;
import com.kissair.model.Employee;
import com.kissair.model.Flight;

/**
 * The Flight JDBC DAO class.
 */
public class FlightJdbcDAO implements FlightDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant SQL_FIND_ALL. */
    private static final String SQL_FIND_ALL =
	    "SELECT * FROM flights;";

    /** The Constant SQL_FIND_BY_ID. */
    private static final String SQL_FIND_BY_ID =
	    "SELECT * FROM flights WHERE id = ?;";

    private static final String SQL_FIND_NEXT =
	    "SELECT * FROM flights WHERE "
		    + "(dep_timestamp BETWEEN NOW() AND NOW() "
		    + "+ INTERVAL ? MINUTE)"
		    + " OR "
		    + "(arr_timestamp BETWEEN NOW() AND NOW() "
		    + "+ INTERVAL ? MINUTE);";

    private static final String SQL_INSERT_FLIGHT =
	    "INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp,"
		    + " arr_timestamp, aircraft_id) VALUES (?, ?, ?, ?, ?);";

    private static final String SQL_INSERT_CREW =
	    "INSERT INTO employees_flights(flight_id, employee_id)"
		    + " VALUES (?, ?);";

    private static final String SQL_UPDATE_FLIGHT =
	    "UPDATE flights SET dep_airport_id = ?, arr_airport_id = ?,"
		    + " dep_timestamp = ?, arr_timestamp = ?, aircraft_id = ?"
		    + " WHERE id = ?;";

    private static final String SQL_IS_CREW_EMPTY =
	    "SELECT id FROM employees_flights WHERE flight_id = ? LIMIT 1;";

    private static final String SQL_DELETE_CREW =
	    "DELETE FROM employees_flights WHERE flight_id = ?;";

    private static final String SQL_FIND_CREW =
	    "SELECT employee_id FROM employees_flights WHERE flight_id = ?;";

    private static final String COL_ID = "id";
    private static final String COL_DEP_AIRP_ID = "dep_airport_id";
    private static final String COL_ARR_AIRP_ID = "arr_airport_id";
    private static final String COL_DEP_TS = "dep_timestamp";
    private static final String COL_ARR_TS = "arr_timestamp";
    private static final String COL_AIRCRAFT_ID = "aircraft_id";

    private static final String COL_EMP_ID = "employee_id";

    /** The DataSource object. */
    private DataSource ds;

    /**
     * Instantiates a new Flight JDBC DAO.
     *
     * @param ds the datasource
     */
    FlightJdbcDAO(DataSource ds) {
	this.ds = ds;
    }

    @Override
    public List<Flight> findAll() {
	List<Flight> result = new ArrayList<>();

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {
	    conn.setAutoCommit(true);

	    try (ResultSet rs = ps.executeQuery()) {
		AirportDAO airportDao = new AirportJdbcDAO(ds);
		AircraftDAO aircraftDao = new AircraftJdbcDAO(ds);

		Flight flight = null;

		while (rs.next()) {
		    flight = new Flight();

		    setUpFlight(flight, rs, airportDao, aircraftDao);
		    result.add(flight);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }

    @Override
    public Flight findById(int id) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
	    conn.setAutoCommit(true);
	    ps.setObject(1, id);

	    try (ResultSet rs = ps.executeQuery()) {
		if (!rs.next()) {
		    LOGGER.warn("No Flight with such an id found");
		    return null;
		}

		Flight flight = new Flight();

		AirportDAO airportDao = new AirportJdbcDAO(ds);
		AircraftDAO aircraftDao = new AircraftJdbcDAO(ds);

		setUpFlight(flight, rs, airportDao, aircraftDao);
		return flight;
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return null;
    }

    @Override
    public List<Flight> findNextFlights(long time, TimeUnit unit) {
	List<Flight> result = new ArrayList<>();

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_NEXT)) {
	    conn.setAutoCommit(true);

	    ps.setObject(1, unit.toMinutes(time));
	    ps.setObject(2, unit.toMinutes(time));

	    try (ResultSet rs = ps.executeQuery()) {
		AirportDAO airportDao = new AirportJdbcDAO(ds);
		AircraftDAO aircraftDao = new AircraftJdbcDAO(ds);

		Flight flight = null;

		while (rs.next()) {
		    flight = new Flight();

		    setUpFlight(flight, rs, airportDao, aircraftDao);
		    result.add(flight);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }

    /**
     * Sets the up a Flight instance using the data from a Result Set.
     *
     * @param flight the flight
     * @param rs the ResultSet
     * @param airportDao the Airport DAO
     * @param aircraftDao the Aircraft DAO
     * @throws SQLException the SQL exception
     */
    private void setUpFlight(Flight flight, ResultSet rs,
	    AirportDAO airportDao, AircraftDAO aircraftDao) throws SQLException {
	flight.setId(rs.getObject(COL_ID, Integer.class));
	flight.setDepTimestamp(
		rs.getObject(COL_DEP_TS, java.sql.Timestamp.class));
	flight.setArrTimestamp(
		rs.getObject(COL_ARR_TS, java.sql.Timestamp.class));
	flight.setDepAirport(airportDao.findById(rs.getObject(
		COL_DEP_AIRP_ID, Integer.class)));
	flight.setArrAirport(airportDao.findById(rs.getObject(
		COL_ARR_AIRP_ID, Integer.class)));
	flight.setAircraft(aircraftDao.findById(rs.getObject(
		COL_AIRCRAFT_ID, Integer.class)));
	flight.setCrew(findCrewForFlight(flight.getId()));
    }

    @Override
    public boolean add(Flight flight) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_INSERT_FLIGHT,
			Statement.RETURN_GENERATED_KEYS);
		PreparedStatement psInsert =
			conn.prepareStatement(SQL_INSERT_CREW)) {
	    conn.setAutoCommit(false);
	    conn.setSavepoint();

	    ps.setObject(1, flight.getDepAirport().getId());
	    ps.setObject(2, flight.getArrAirport().getId());
	    ps.setObject(3, 
		    new java.sql.Timestamp(flight.getDepTimestamp().getTime()));
	    ps.setObject(4, 
		    new java.sql.Timestamp(flight.getArrTimestamp().getTime()));
	    ps.setObject(5, flight.getAircraft().getId());

	    ps.executeUpdate();

	    try (ResultSet keys = ps.getGeneratedKeys()) {
		if (!keys.next()) {
		    LOGGER.warn("Could not insert Flight data");
		    conn.rollback();
		    return false;
		}

		int id = keys.getInt(1);

		flight.setId(id);

		for (Employee e : flight.getCrew()) {
		    psInsert.setObject(1, flight.getId());
		    psInsert.setObject(2, e.getId());

		    if (psInsert.executeUpdate() == 0) {
			LOGGER.warn("Could not insert Employee-Flight "
				+ "association data");
			conn.rollback();
			return false;
		    }
		}

		conn.commit();
		return true;
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return false;
    }

    @Override
    public boolean update(Flight flight) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = 
			conn.prepareStatement(SQL_UPDATE_FLIGHT);
		PreparedStatement psDelete = 
			conn.prepareStatement(SQL_DELETE_CREW);
		PreparedStatement psEmpty = 
			conn.prepareStatement(SQL_IS_CREW_EMPTY);
		PreparedStatement psInsert = 
			conn.prepareStatement(SQL_INSERT_CREW)) {
	    conn.setAutoCommit(false);
	    conn.setSavepoint();

	    ps.setObject(1, flight.getDepAirport().getId());
	    ps.setObject(2, flight.getArrAirport().getId());
	    ps.setObject(3, flight.getDepTimestamp());
	    ps.setObject(4, flight.getArrTimestamp());
	    ps.setObject(5, flight.getAircraft().getId());
	    ps.setObject(6, flight.getId());

	    if (ps.executeUpdate() == 0) {
		LOGGER.warn("Could not update Flight data");
		conn.rollback();
		return false;
	    }

	    psDelete.setObject(1, flight.getId());
	    psDelete.executeUpdate();

	    psEmpty.setObject(1, flight.getId());

	    if (psEmpty.executeQuery().first()) {
		LOGGER.warn("Could not delete Employee-Flight data");
		conn.rollback();
		return false;
	    }

	    for (Employee e : flight.getCrew()) {
		psInsert.setObject(1, flight.getId());
		psInsert.setObject(2, e.getId());

		if (psInsert.executeUpdate() == 0) {
		    LOGGER.warn("Could not insert Employee-Flight data");
		    conn.rollback();
		    return false;
		}
	    }

	    conn.commit();
	    return true;
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return false;
    }

    @Override
    public Set<Employee> findCrewForFlight(Flight f) {
	return findCrewForFlight(f.getId());
    }

    @Override
    public Set<Employee> findCrewForFlight(int id) {
	Set<Employee> result = new HashSet<>();
	EmployeeDAO employeeDao = new EmployeeJdbcDAO(ds);

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_CREW)) {
	    conn.setAutoCommit(true);
	    ps.setObject(1, id);

	    try (ResultSet rs = ps.executeQuery()) {
		Employee employee = null;

		while (rs.next()) {
		    employee = employeeDao.findById(
			    rs.getObject(COL_EMP_ID, Integer.class));

		    result.add(employee);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }
}
