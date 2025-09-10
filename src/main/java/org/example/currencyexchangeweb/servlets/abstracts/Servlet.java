package org.example.currencyexchangeweb.servlets.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.exeptions.MethodException;

import java.io.IOException;

public abstract class Servlet extends HttpServlet {

    private static final String ERROR_METHOD_MESSAGE = "Method not implemented.";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PATCH = "PATCH";
    private static final String METHOD_DELETE = "DELETE";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodException(METHOD_DELETE + " " + ERROR_METHOD_MESSAGE);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodException(METHOD_PATCH + " " + ERROR_METHOD_MESSAGE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        throw new MethodException(METHOD_POST + " " + ERROR_METHOD_MESSAGE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodException(METHOD_GET + " " + ERROR_METHOD_MESSAGE);
    }

    @Override
    protected final void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method) {
            case METHOD_GET -> doGet(req, resp);
            case METHOD_POST -> doPost(req, resp);
            case METHOD_PATCH -> doPatch(req, resp);
            case METHOD_DELETE -> doDelete(req,resp);
            case null, default -> throw new MethodException(method + " " + ERROR_METHOD_MESSAGE);
        }
    }

    protected final void responseObject(HttpServletResponse resp, Object object) throws IOException {
        objectMapper.writeValue(resp.getWriter(), object);
    }
}
