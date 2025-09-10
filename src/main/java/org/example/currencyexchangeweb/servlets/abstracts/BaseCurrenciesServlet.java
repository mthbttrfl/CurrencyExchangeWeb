package org.example.currencyexchangeweb.servlets.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.servies.CurrenciesService;
import org.example.currencyexchangeweb.validators.CurrencyValidator;
import org.example.currencyexchangeweb.validators.DefaultValidator;
import org.example.currencyexchangeweb.validators.PathValidator;

public abstract class BaseCurrenciesServlet extends Servlet {

    private static final String PARAM_CURRENCIES_CODE = "code";
    private static final String PARAM_CURRENCIES_NAME = "name";
    private static final String PARAM_CURRENCIES_SIGN = "sign";

    private final PathValidator pathValidator = PathValidator.getInstance();
    private final DefaultValidator<RequestCurrencyDTO> dtoValidator = CurrencyValidator.getInstance();

    protected final CurrenciesService service = CurrenciesService.getInstance();

    protected final RequestCurrencyDTO getDtoByParameters(HttpServletRequest req){
        String code = req.getParameter(PARAM_CURRENCIES_CODE);
        String name = req.getParameter(PARAM_CURRENCIES_NAME);
        String sign = req.getParameter(PARAM_CURRENCIES_SIGN);

        RequestCurrencyDTO dto = new RequestCurrencyDTO(code,name, sign);
        dtoValidator.validate(dto);

        return dto;
    }

    protected final String getCodeByPath(HttpServletRequest req){
        String path = req.getPathInfo();
        pathValidator.setSize(3);
        pathValidator.validate(path);

        return path.replaceAll("/","");
    }

    protected final RequestCurrencyDTO getDtoByCode(String code){
        RequestCurrencyDTO dto = new RequestCurrencyDTO(code);
        dtoValidator.validate(dto);

        return dto;
    }
}