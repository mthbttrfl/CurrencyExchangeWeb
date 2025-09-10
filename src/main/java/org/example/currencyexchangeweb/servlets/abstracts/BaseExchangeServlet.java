package org.example.currencyexchangeweb.servlets.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.servies.ExchangeService;
import org.example.currencyexchangeweb.validators.ExchangeRatesValidator;
import org.example.currencyexchangeweb.validators.NumericalValidator;
import org.example.currencyexchangeweb.validators.Validator;

import java.math.BigDecimal;

public abstract class BaseExchangeServlet extends Servlet {

    private static final String PARAM_EXCHANGE_FROM = "from";
    private static final String PARAM_EXCHANGE_TO = "to";
    private static final String PARAM_EXCHANGE_AMOUNT = "amount";

    private final Validator<String> amountValidator = NumericalValidator.getInstance();
    private final Validator<RequestExchangeRateDTO> dtoValidator = ExchangeRatesValidator.getInstance();

    protected final ExchangeService service = ExchangeService.getInstance();

    protected final RequestExchangeDTO getDtoByParameters(HttpServletRequest req){
        String baseCode = req.getParameter(PARAM_EXCHANGE_FROM);
        String targetCode = req.getParameter(PARAM_EXCHANGE_TO);
        String amount = req.getParameter(PARAM_EXCHANGE_AMOUNT);
        amountValidator.validate(amount);

        BigDecimal amountDto = new BigDecimal(amount);

        RequestExchangeRateDTO exchangeRatesDTO = new RequestExchangeRateDTO(baseCode, targetCode);
        dtoValidator.validate(exchangeRatesDTO);

        return new RequestExchangeDTO(exchangeRatesDTO, amountDto);
    }
}
