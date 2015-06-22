package com.kissair.dao.hibernate;

import com.kissair.dao.AircraftDAO;
import com.kissair.model.Aircraft;

/**
 * The Aircraft Hibernate DAO class.
 */
public class AircraftHibDAO extends HibDAO<Aircraft> implements AircraftDAO{

    /**
     * Instantiates a new Aircraft Hibernate DAO.
     */
    AircraftHibDAO() {
	super(Aircraft.class);
    }
}
