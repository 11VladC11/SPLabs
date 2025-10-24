package com.designpatterns.lab.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestId = String.valueOf(System.currentTimeMillis());
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String timestamp = LocalDateTime.now().format(formatter);
        
        // Log BEFORE processing
        logger.info("[BEFORE] [{}] {} {} {} - Started", 
                    requestId, timestamp, method, uri);
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Continue the filter chain (actual request processing)
            chain.doFilter(request, response);
        } finally {
            // Log AFTER processing
            long duration = System.currentTimeMillis() - startTime;
            int status = httpResponse.getStatus();
            String endTimestamp = LocalDateTime.now().format(formatter);
            
            logger.info("[AFTER] [{}] {} {} {} - Status: {} - Duration: {}ms", 
                        requestId, endTimestamp, method, uri, status, duration);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestLoggingFilter initialized");
    }

    @Override
    public void destroy() {
        logger.info("RequestLoggingFilter destroyed");
    }
}