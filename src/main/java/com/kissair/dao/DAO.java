package com.kissair.dao;

import java.util.Collection;
import java.util.List;

/**
 * The DAO Interface.
 *
 * @param <T> the generic type
 */
public interface DAO<T> {
    
    /**
     * Load all corresponding objects from the data source.
     *
     * @return the list
     */
    default List<T> findAll() {
	return null;
    }
    
    /**
     * Load an object from the data source which has the specified id value.
     *
     * @param id the id
     * @return the object with the given id
     */
    default T findById(int id) {
	return null;
    }
    
    /**
     * Save an object to the data source.
     *
     * @param obj the object
     * @return true, if successful
     */
    default boolean add(T obj) {
	return false;
    }
    
    /**
     * Save a collection of objects to the data source.
     *
     * @param col the collection of objects
     * @return true, if successful
     */
    default boolean add(Collection<? extends T> col) {
	return false;
    }
    
    /**
     * Delete an object from the data source.
     *
     * @param obj the object
     * @return true, if successful
     */
    default boolean delete(T obj) {
	return false;
    }
    
    /**
     * Delete a collection of objects from the data source.
     *
     * @param col the collection of objects
     * @return true, if successful
     */
    default boolean delete(Collection<? extends T> col) {
	return false;
    }
    
    /**
     * Update an object in the data source.
     *
     * @param obj the object
     * @return true, if successful
     */
    default boolean update(T obj) {
	return false;
    }
    
    /**
     * Update a collection of objects in the data source.
     *
     * @param col the collection
     * @return true, if successful
     */
    default boolean update(Collection<? extends T> col) {
	return false;
    }
}
