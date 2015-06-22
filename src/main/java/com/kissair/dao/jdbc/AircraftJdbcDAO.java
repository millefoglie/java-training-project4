/*
 * 
 */
package com.kissair.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.AircraftDAO;
import com.kissair.model.Aircraft;

/**
 * The Aircraft JDBC DAO.
 */
public class AircraftJdbcDAO implements AircraftDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant SQL_FIND_ALL. */
    private static final String SQL_FIND_ALL =
	    "SELECT * FROM aircrafts;";

    /** The Constant SQL_FIND_BY_ID. */
    private static final String SQL_FIND_BY_ID =
	    "SELECT * FROM aircrafts WHERE id = ?;";

    private static final String COL_ID = "id";
    private static final String COL_MODEL = "model";
    private static final String COL_REG_NAME = "reg_name";
    private static final String COL_MANUF = "manufacturer";
    private static final String COL_MANUF_DATE = "manufacture_date";
    private static final String COL_MANUF_COUNTRY = "manufacture_country";
    private static final String COL_SPEED = "speed";
    private static final String COL_MAX_DIST = "max_dist";
    private static final String COL_CABIN_CAP = "cabin_capacity";
    private static final String COL_PASS_CAP = "pass_capacity";

    /** The DataSource object. */
    private DataSource ds;

    /**
     * Instantiates a new Aircraft JDBC DAO.
     *
     * @param ds the datasource
     */
    AircraftJdbcDAO(DataSource ds) {
	this.ds = ds;
    }

    @Override
    public List<Aircraft> findAll() {
	List<Aircraft> result = new ArrayList<>();

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {
	    conn.setAutoCommit(true);

	    try (ResultSet rs = ps.executeQuery()) {
		Aircraft aircraft = null;

		while (rs.next()) {
		    aircraft = new Aircraft();

		    setUpAircraft(aircraft, rs);
		    result.add(aircraft);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }

    @Override
    public Aircraft findById(int id) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
	    conn.setAutoCommit(true);

	    ps.setObject(1, id);

	    try (ResultSet rs = ps.executeQuery()) {
		if (!rs.next()) {
		    LOGGER.warn("No Aircraft with such an id found");
		    return null;
		}

		Aircraft aircraft = new Aircraft();

		setUpAircraft(aircraft, rs);
		return aircraft;
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return null;
    }

    /**
     * Sets the up an Aircraft instance using the data from a Result Set.
     *
     * @param aircraft the aircraft
     * @param rs the Result Set
     * @throws SQLException the SQL exception
     */
    private void setUpAircraft(Aircraft aircraft, ResultSet rs)
	    throws SQLException {
	aircraft.setId(
		rs.getObject(COL_ID, Integer.class));
	aircraft.setModel(
		rs.getObject(COL_MODEL, String.class));
	aircraft.setRegName(
		rs.getObject(COL_REG_NAME, String.class));
	aircraft.setManufacturer(
		rs.getObject(COL_MANUF, String.class));
	aircraft.setManufactureDate(new Date(
		rs.getObject(COL_MANUF_DATE, java.sql.Date.class).getTime()));
	aircraft.setManufactureCountry(
		rs.getObject(COL_MANUF_COUNTRY, String.class));
	aircraft.setSpeed(
		rs.getObject(COL_SPEED, Float.class));
	aircraft.setMaxDist(
		rs.getObject(COL_MAX_DIST, Float.class));
	aircraft.setCabinCapacity(
		rs.getObject(COL_CABIN_CAP, Integer.class));
	aircraft.setPassCapacity(
		rs.getObject(COL_PASS_CAP, Integer.class));
    }
}
