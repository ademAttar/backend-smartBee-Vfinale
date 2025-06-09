package com.mycompany.smart.beee.house.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORS Filter to handle cross-origin requests between the frontend and backend
 * This filter adds the necessary headers to all HTTP responses
 */
public class CORSFilter implements Filter {

    /**
     * Initialize the filter
     * @param filterConfig Filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    /**
     * Apply CORS headers to all responses
     * @param request Servlet request
     * @param response Servlet response
     * @param chain Filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Allow requests from the Angular frontend origin
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        
        // Allow specific HTTP methods
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        // Allow specific headers in requests
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // Allow credentials (cookies, authorization headers, etc.)
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        
        // Set max age for preflight requests
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        
        // For preflight requests (OPTIONS)
        if (httpRequest.getMethod().equals("OPTIONS")) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        
        // Pass the request along the filter chain for non-preflight requests
        chain.doFilter(request, response);
    }

    /**
     * Clean up resources when filter is destroyed
     */
    @Override
    public void destroy() {
        // No resources to clean up
    }
}