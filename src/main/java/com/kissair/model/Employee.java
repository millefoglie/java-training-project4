package com.kissair.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * The Employee class.
 */
public class Employee implements Serializable{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 150868682470613263L;
    
    /** The id. */
    private int id;
    
    /** The registration code. */
    private String regCode;
    
    /** The name. */
    private String name;
    
    /** The surname. */
    private String surname;
    
    /** The gender. */
    private Gender gender;
    
    /** The position. */
    private Position position;
    
    /** The salary. */
    private BigDecimal salary;
    
    /** The date of birth. */
    private Date dateOfBirth;
    
    /** The flights performed. */
    private int flightsPerformed;
    
    /** The associated flights. */
    private Set<Flight> flights;
    
    /**
     * The Gender enumeration.
     */
    public enum Gender {
	    M, F;
    }
    
    /**
     * Instantiates a new employee.
     */
    public Employee() {}

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
     * Gets the registration code.
     *
     * @return the registration code
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * Sets the registration code.
     *
     * @param regCode the new registration code
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode;
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

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the salary.
     *
     * @return the salary
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * Sets the salary.
     *
     * @param salary the new salary
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the flights performed.
     *
     * @return the flights performed
     */
    public int getFlightsPerformed() {
        return flightsPerformed;
    }

    /**
     * Sets the flights performed.
     *
     * @param flightsPerformed the new flights performed
     */
    public void setFlightsPerformed(int flightsPerformed) {
        this.flightsPerformed = flightsPerformed;
    }

    /**
     * Gets the associated flights.
     *
     * @return the associated flights
     */
    public Set<Flight> getFlights() {
        return flights;
    }

    /**
     * Sets the associated flights.
     *
     * @param flights the new associated flights
     */
    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }
}
