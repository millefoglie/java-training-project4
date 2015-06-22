package com.kissair.dao.hibernate;

import com.kissair.dao.EmployeeDAO;
import com.kissair.model.Employee;

/**
 * The Employee Hibernate DAO class.
 */
public class EmployeeHibDAO extends HibDAO<Employee> implements EmployeeDAO {

    /**
     * Instantiates a new Employee Hibernate DAO.
     */
    EmployeeHibDAO() {
	super(Employee.class);
    }
}
