package com.kissair.controllers;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The servlet for signing a user out.
 */
public class SignOutServlet extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3400952154758712751L;
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

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
     * Handle request. Sign the current user out and invalidate its session.
     *
     * @param req the request
     * @param resp the response
     * @throws ServletException the servlet exception
     */
    private void handleRequest(HttpServletRequest req,
	    HttpServletResponse resp) throws ServletException {
	Principal principal = req.getUserPrincipal();
	
	if (principal != null) {
	    req.logout();
	    req.getSession().invalidate();
	}
	
	try {
	    resp.sendRedirect("/index.jsp");
	} catch (IOException e) {
	    LOGGER.error(e);
	}
    }
}
