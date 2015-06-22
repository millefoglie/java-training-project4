package com.kissair.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The servlet for setting current session settings, like a locale.
 */
public class SettingsServlet extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6409342264354267554L;
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /** The Constant LOCALE_KEY. */
    private static final String LOCALE_KEY = "locale";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	handleRequest(req, resp);
    }
    
    /**
     * Handle request. Change the locale for the current session to the
     * one provided as a GET parameter. If such an operation cannot be
     * performed, then nothing is done. In any case there is a redirect
     * to the index page.
     *
     * @param req the request
     * @param resp the response
     */
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
	String localeName = req.getParameter(LOCALE_KEY);
	
	if (localeName != null) {
	    Locale locale = Locale.forLanguageTag(localeName);

	    if (locale != null) {
		Config.set(req.getSession(), Config.FMT_LOCALE, locale);
		req.getSession().setAttribute(LOCALE_KEY, locale);
	    }
	}

	try {
	    resp.sendRedirect("/index.jsp");
	} catch (IOException e) {
	    LOGGER.error(e);
	}
    }
}
