package com.kissair.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.AirportDAO;
import com.kissair.model.Airport;

/**
 * The Airport JDBC DAO class.
 */
public class AirportJdbcDAO implements AirportDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant SQL_FIND_ALL. */
    private static final String SQL_FIND_ALL =
	    "SELECT * FROM airports;";

    /** The Constant SQL_FIND_BY_ID. */
    private static final String SQL_FIND_BY_ID =
	    "SELECT * FROM airports WHERE id = ?;";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_IATA_FAA = "iata_faa";
    private static final String COL_ICAO = "icao";
    private static final String COL_LAT = "latitude";
    private static final String COL_LONG = "longitude";
    private static final String COL_CITY = "city";
    private static final String COL_COUNTRY = "country";

    /** The DataSource object. */
    private DataSource ds;

    /**
     * Instantiates a new Airport JDBC DAO.
     *
     * @param ds the datasource
     */
    AirportJdbcDAO(DataSource ds) {
	this.ds = ds;
    }

    @Override
    public List<Airport> findAll() {
	List<Airport> result = new ArrayList<>();

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {
	    conn.setAutoCommit(true);

	    try (ResultSet rs = ps.executeQuery()) {
		Airport airport = null;

		while (rs.next()) {
		    airport = new Airport();

		    setUpAirport(airport, rs);
		    result.add(airport);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }

    @Override
    public Airport findById(int id) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
	    conn.setAutoCommit(true);

	    ps.setObject(1, id);

	    try (ResultSet rs = ps.executeQuery()) {
		if (!rs.next()) {
		    LOGGER.warn("No Airport with such an id found");
		    return null;
		}

		Airport airport = new Airport();
		setUpAirport(airport, rs);

		return airport;
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return null;
    }

    /**
     * Sets the up an Airport instance using the data from a Result Set.
     *
     * @param airport the airport
     * @param rs the Result Set
     * @throws SQLException the SQL exception
     */
    private void setUpAirport(Airport airport, ResultSet rs)
	    throws SQLException {
	airport.setId(rs.getObject(COL_ID, Integer.class));
	airport.setName(rs.getObject(COL_NAME, String.class));
	airport.setIataFaa(rs.getObject(COL_IATA_FAA, String.class));
	airport.setIcao(rs.getObject(COL_ICAO, String.class));
	airport.setLatitude(rs.getObject(COL_LAT, BigDecimal.class));
	airport.setLongitude(rs.getObject(COL_LONG, BigDecimal.class));
	airport.setCity(rs.getObject(COL_CITY, String.class));
	airport.setCountry(rs.getObject(COL_COUNTRY, String.class));
    }
}
