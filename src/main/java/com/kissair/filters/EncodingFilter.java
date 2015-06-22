package com.kissair.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The filter for changing requests and responses encoding.
 */
public class EncodingFilter implements Filter {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();
    
    /** The Constant ENCODING UTF-8. */
    private static final String ENCODING = "UTF-8";
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
	LOGGER.info("EncodingFilter was initiated.");
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	req.setCharacterEncoding(ENCODING);
	resp.setCharacterEncoding(ENCODING);
	chain.doFilter(req, resp);
    }
    
    @Override
    public void destroy() {
	LOGGER.info("EncodingFilter was destroyed.");
    }
}
