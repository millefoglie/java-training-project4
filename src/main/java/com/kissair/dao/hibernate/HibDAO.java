package com.kissair.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.kissair.dao.DAO;
import com.kissair.util.HibernateUtil;

/**
 * The Hibernate DAO class.
 *
 * @param <T> the generic type
 */
public class HibDAO<T> implements DAO<T> {

    /** The class of processed objects. */
    private Class<T> clazz;

    /**
     * Instantiates a new Hibernate DAO.
     *
     * @param clazz the class of processed objects
     */
    HibDAO(Class<T> clazz) {
	this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
	session.beginTransaction();

	List<T> result = session.createCriteria(clazz)
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.list();

	session.getTransaction().commit();
	return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T findById(int id) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
	session.beginTransaction();

	T result = (T) session.get(clazz, id);

	session.getTransaction().commit();
	return result;
    }

    @Override
    public boolean add(T obj) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	session.beginTransaction();
	session.save(obj);
	session.getTransaction().commit();
	return true;
    }

    @Override
    public boolean delete(T obj) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	session.beginTransaction();
	session.delete(obj);
	session.getTransaction().commit();
	return true;
    }

    @Override
    public boolean update(T obj) {
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	session.beginTransaction();
	session.update(obj);
	session.getTransaction().commit();
	return true;
    }
}
