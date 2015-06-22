package com.kissair.dao.hibernate;

import com.kissair.dao.AirportDAO;
import com.kissair.model.Airport;

/**
 * The Airport Hibernate DAO class.
 */
public class AirportHibDAO extends HibDAO<Airport> implements AirportDAO {

    /**
     * Instantiates a new Airport Hibernate DAO.
     */
    AirportHibDAO() {
	super(Airport.class);
    }
}
