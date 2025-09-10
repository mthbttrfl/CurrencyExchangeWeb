package org.example.currencyexchangeweb.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.response.ExceptionResponse;
import org.example.currencyexchangeweb.exeptions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@WebFilter("/*")
public final class ExceptionHandleFilter implements Filter {

    private final static String NOT_FOUND_MESSAGE = "Not Found.";
    private final static String SERVER_MESSAGE = "Server Error.";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception ex){
            handleException(ex, (HttpServletResponse) servletResponse);
        }
    }

    private void handleException(Exception ex, HttpServletResponse resp) throws IOException {
        if(ex instanceof PropertiesException || ex instanceof ConnectionException){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(ex.getMessage()));
        }

        if(ex instanceof NoSuchElementException){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(NOT_FOUND_MESSAGE));
        }

        if(ex instanceof ValidatorException){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(ex.getMessage()));
        }

        if(ex instanceof DaoException){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(((DaoException) ex).getDecodedMessage()));
        }

        if(ex instanceof MethodException){
            resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(ex.getMessage()));
        }else{
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ExceptionResponse(SERVER_MESSAGE + " " + Arrays.toString(ex.getStackTrace())));
        }
    }
}
