package com.kissair.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The Airport class.
 */
public class Airport implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 29571229027685121L;

    /** The id. */
    private int id;
    
    /** The name. */
    private String name;
    
    /** The IATA/FAA code. */
    private String iataFaa;
    
    /** The ICAO code. */
    private String icao;
    
    /** The latitude. */
    private BigDecimal latitude;
    
    /** The longitude. */
    private BigDecimal longitude;
    
    /** The city. */
    private String city;
    
    /** The country. */
    private String country;
    
    /**
     * Instantiates a new airport.
     */
    public Airport() {}

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

    /**
     * Gets the IATA/FAA code.
     *
     * @return the IATA/FAA code
     */
    public String getIataFaa() {
        return iataFaa;
    }

    /**
     * Sets the IATA/FAA code.
     *
     * @param iataFaa the new IATA/FAA code
     */
    public void setIataFaa(String iataFaa) {
        this.iataFaa = iataFaa;
    }

    /**
     * Gets the ICAO code.
     *
     * @return the ICAO code
     */
    public String getIcao() {
        return icao;
    }

    /**
     * Sets the ICAO code.
     *
     * @param icao the new ICAO code
     */
    public void setIcao(String icao) {
        this.icao = icao;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude.
     *
     * @param lattitude the new latitude
     */
    public void setLatitude(BigDecimal lattitude) {
        this.latitude = lattitude;
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude the new longitude
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the new country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
