package com.kissair.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The filter for logging remote host requests to the web application.
 */
public class SecurityFilter implements Filter{
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER 
    	= LogManager.getLogger("org.example.security");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	LogManager.getLogger().info("SecurityFilter was initiated.");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	StringBuilder sb = new StringBuilder();
	
	if (req instanceof HttpServletRequest) {
	    HttpServletRequest httpReq = (HttpServletRequest) req;
	    
	    String host = httpReq.getRemoteAddr();
	    String user = httpReq.getRemoteUser();
	    String uri =  httpReq.getRequestURI();
	    
	    sb.append(user != null ? user : "Someone ")
	    	.append(" at ")
	    	.append(host)
	    	.append(" made a request to ")
	    	.append(uri);
	    
	    LOGGER.info(sb.toString());
	} else {
	    sb.append(req.getRemoteHost()).append(" made a request");
	    LOGGER.info(sb.toString());
	}
	
	chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
	LogManager.getLogger().info("SecurityFilter was destroyed.");
    }
}
