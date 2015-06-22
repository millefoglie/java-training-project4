package com.kissair.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Aircraft class.
 */
public class Aircraft implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4412277659515311661L;

    /** The id. */
    private int id;
    
    /** The model. */
    private String model;
    
    /** The registration name. */
    private String regName;
    
    /** The manufacturer. */
    private String manufacturer;
    
    /** The manufacture date. */
    private Date manufactureDate;
    
    /** The manufacture country. */
    private String manufactureCountry;
    
    /** The speed. */
    private float speed;
    
    /** The max flight distance. */
    private float maxDist;
    
    /** The cabin capacity. */
    private int cabinCapacity;
    
    /** The passengers capacity. */
    private int passCapacity;
    
    /**
     * Instantiates a new aircraft.
     */
    public Aircraft() {}

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
     * Gets the model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the registration name.
     *
     * @return the registration name
     */
    public String getRegName() {
        return regName;
    }

    /**
     * Sets the registration name.
     *
     * @param regName the new registration name
     */
    public void setRegName(String regName) {
        this.regName = regName;
    }

    /**
     * Gets the manufacturer.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the manufacturer.
     *
     * @param manufacturer the new manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets the manufacture date.
     *
     * @return the manufacture date
     */
    public Date getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Sets the manufacture date.
     *
     * @param manufactureDate the new manufacture date
     */
    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * Gets the manufacture country.
     *
     * @return the manufacture country
     */
    public String getManufactureCountry() {
        return manufactureCountry;
    }

    /**
     * Sets the manufacture country.
     *
     * @param manufactureCountry the new manufacture country
     */
    public void setManufactureCountry(String manufactureCountry) {
        this.manufactureCountry = manufactureCountry;
    }

    /**
     * Gets the speed.
     *
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed.
     *
     * @param speed the new speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the max flight dist.
     *
     * @return the max flight dist
     */
    public float getMaxDist() {
        return maxDist;
    }

    /**
     * Sets the max flight dist.
     *
     * @param maxDist the new max flight dist
     */
    public void setMaxDist(float maxDist) {
        this.maxDist = maxDist;
    }

    /**
     * Gets the cabin capacity.
     *
     * @return the cabin capacity
     */
    public int getCabinCapacity() {
        return cabinCapacity;
    }

    /**
     * Sets the cabin capacity.
     *
     * @param cabinCapacity the new cabin capacity
     */
    public void setCabinCapacity(int cabinCapacity) {
        this.cabinCapacity = cabinCapacity;
    }

    /**
     * Gets the passengers capacity.
     *
     * @return the passengers capacity
     */
    public int getPassCapacity() {
        return passCapacity;
    }

    /**
     * Sets the passengers capacity.
     *
     * @param passCapacity the new passengers capacity
     */
    public void setPassCapacity(int passCapacity) {
        this.passCapacity = passCapacity;
    }
}
