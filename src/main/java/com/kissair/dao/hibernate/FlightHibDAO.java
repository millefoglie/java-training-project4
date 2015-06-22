package com.kissair.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.kissair.dao.FlightDAO;
import com.kissair.model.Employee;
import com.kissair.model.Flight;
import com.kissair.util.HibernateUtil;

/**
 * The Flight Hibernate DAO.
 */
public class FlightHibDAO extends HibDAO<Flight> implements FlightDAO {

    /**
     * Instantiates a new Flight Hibernate DAO.
     */
    FlightHibDAO() {
	super(Flight.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Flight> findNextFlights(long time, TimeUnit unit) {
	Calendar cal = Calendar.getInstance();
	
	cal.add(Calendar.MINUTE, (int) unit.toMinutes(time));
	
	Date now = new Date();
	Date until = cal.getTime();
	
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
	session.beginTransaction();

	List<Flight> result = new ArrayList<>();
	
	Criterion criterionDep 
		= Restrictions.and(Restrictions.gt("depTimestamp", now),
		Restrictions.lt("depTimestamp", until));
	Criterion criterionArr
		= Restrictions.and(Restrictions.gt("arrTimestamp", now),
		Restrictions.lt("arrTimestamp", until));
	Criterion criterion = Restrictions.or(criterionDep, criterionArr);
	
	result.addAll(session.createCriteria(Flight.class)
		.add(criterion)
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.list());

	session.getTransaction().commit();
	return result;
    }

    @Override
    public Set<Employee> findCrewForFlight(Flight f) {
	return findCrewForFlight(f.getId());
    }

    @Override
    public Set<Employee> findCrewForFlight(int id) {
	Set<Employee> result = null;
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	session.beginTransaction();

	Flight f = (Flight) session.get(Flight.class, id);
	result = f.getCrew();
	
	session.getTransaction().commit();
	return result;
    }
}
