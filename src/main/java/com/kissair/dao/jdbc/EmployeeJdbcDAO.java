package com.kissair.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kissair.dao.EmployeeDAO;
import com.kissair.model.Employee;
import com.kissair.model.Position;

/**
 * The Employee JDBC DAO class.
 */
public class EmployeeJdbcDAO implements EmployeeDAO {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The Constant SQL_FIND_ALL. */
    private static final String SQL_FIND_ALL =
	    "SELECT * FROM employees JOIN positions "
		    + "ON employees.position_id = positions.id;";

    /** The Constant SQL_FIND_BY_ID. */
    private static final String SQL_FIND_BY_ID =
	    "SELECT * FROM employees JOIN positions "
		    + "ON employees.position_id = positions.id "
		    + "WHERE employees.id = ?;";

    private static final String COL_ID = "id";
    private static final String COL_REG_CODE = "reg_code";
    private static final String COL_NAME = "employees.name";
    private static final String COL_SURNAME = "surname";
    private static final String COL_GENDER = "gender";
    private static final String COL_POS_ID = "position_id";
    private static final String COL_SALARY = "salary";
    private static final String COL_DOB = "date_of_birth";
    private static final String COL_FLIGHTS_PERF = "flights_performed";
    private static final String COL_POS_NAME = "positions.name";

    /** The DataSource object. */
    private DataSource ds;

    /**
     * Instantiates a new Employee JDBC DAO.
     *
     * @param ds the datasource
     */
    EmployeeJdbcDAO(DataSource ds) {
	this.ds = ds;
    }

    @Override
    public List<Employee> findAll() {
	List<Employee> result = new ArrayList<>();

	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL)) {
	    conn.setAutoCommit(true);

	    try (ResultSet rs = ps.executeQuery()) {
		Employee employee = null;

		while (rs.next()) {
		    employee = new Employee();

		    setUpEmployee(employee, rs);
		    result.add(employee);
		}
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return result;
    }

    @Override
    public Employee findById(int id) {
	try (Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
	    conn.setAutoCommit(true);
	    ps.setObject(1, id);

	    try (ResultSet rs = ps.executeQuery()) {
		if (!rs.next()) {
		    LOGGER.warn("No Employee with such an id found");
		    return null;
		}

		Employee employee = new Employee();

		setUpEmployee(employee, rs);
		return employee;
	    }
	} catch (SQLException e) {
	    LOGGER.error(e);
	}

	return null;
    }

    /**
     * Sets the up an Employee instance using the data from a Result Set.
     *
     * @param employee the employee
     * @param rs the Result Set
     * @throws SQLException the SQL exception
     */
    private void setUpEmployee(Employee employee, ResultSet rs)
	    throws SQLException {
	employee.setId(
		rs.getObject(COL_ID, Integer.class));
	employee.setRegCode(
		rs.getObject(COL_REG_CODE, String.class));
	employee.setName(
		rs.getObject(COL_NAME, String.class));
	employee.setSurname(
		rs.getObject(COL_SURNAME, String.class));
	employee.setGender(Employee.Gender.valueOf(
		rs.getObject(COL_GENDER, String.class)));
	employee.setSalary(
		rs.getObject(COL_SALARY, BigDecimal.class));
	employee.setDateOfBirth(new Date(
		rs.getObject(COL_DOB, java.sql.Date.class).getTime()));
	employee.setFlightsPerformed(
		rs.getObject(COL_FLIGHTS_PERF, Integer.class));

	Position position = new Position();

	position.setId(rs.getObject(COL_POS_ID, Integer.class));
	position.setName(rs.getObject(COL_POS_NAME, String.class));

	employee.setPosition(position);
    }
}
