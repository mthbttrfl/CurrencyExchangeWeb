package org.example.currencyexchangeweb.servlets.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currencyexchangeweb.exeptions.ValidatorException;
import org.example.currencyexchangeweb.validators.AdminUserValidator;
import org.example.currencyexchangeweb.validators.Validator;

public abstract class AdminServlet extends Servlet {

    private static final String PARAM_ADMIN_USERNAME = "username";
    private static final String PARAM_ADMIN_PASSWORD = "password";
    private static final String PARAM_ADMIN_ID = "id";

    private static final String MESSAGE_ID = "Incorrect id format.";

    private final Validator<String> adminValidator = AdminUserValidator.getInstance();

    protected final void checkAdmin(HttpServletRequest req){
        String username = req.getParameter(PARAM_ADMIN_USERNAME);
        String password = req.getParameter(PARAM_ADMIN_PASSWORD);

        adminValidator.validate(username+password);
    }

    protected final Long getParameterId(HttpServletRequest req){
        String key = req.getParameter(PARAM_ADMIN_ID);

        if(key == null){
            throw new ValidatorException(MESSAGE_ID);
        }

        try {
            return Long.parseLong(key);
        }catch (Exception ex){
            throw new ValidatorException(MESSAGE_ID);
        }
    }
}