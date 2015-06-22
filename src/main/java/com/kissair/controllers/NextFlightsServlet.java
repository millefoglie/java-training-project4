package com.kissair.controllers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kissair.dao.DAOFactory;
import com.kissair.dao.FlightDAO;
import com.kissair.model.Flight;

/**
 * The servlet which adds next flights (in 24 hours) to the request, so that
 * the respective data could be output on the index page.
 */
public class NextFlightsServlet extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7261093365431324795L;
    
    /** The Constant FLIGHTS_KEY. */
    private static final String FLIGHTS_KEY = "flights";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	handleRequest(req, resp);
    }
    
    /**
     * Handle request. Gets next flights and sets them as the request attrubute.
     *
     * @param req the request
     * @param resp the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void handleRequest(HttpServletRequest req,
	    HttpServletResponse resp) throws ServletException, IOException {
	FlightDAO dao = DAOFactory.getFlightDAO();
	List<Flight> flights = dao.findNextFlights(1, TimeUnit.DAYS);
	
	req.setAttribute(FLIGHTS_KEY, flights);
    }
}
