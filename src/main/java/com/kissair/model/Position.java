package com.kissair.model;

import java.io.Serializable;

/**
 * The Position class.
 */
public class Position implements Serializable{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2619056393030448885L;
    
    /** The id. */
    private int id;
    
    /** The name. */
    private String name;
    
    /**
     * Instantiates a new position.
     */
    public Position() {}

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
