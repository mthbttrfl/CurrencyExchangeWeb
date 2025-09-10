package org.example.currencyexchangeweb.servlets.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.exeptions.ValidatorException;
import org.example.currencyexchangeweb.servies.ExchangeRatesService;
import org.example.currencyexchangeweb.validators.ExchangeRatesValidator;
import org.example.currencyexchangeweb.validators.NumericalValidator;
import org.example.currencyexchangeweb.validators.PathValidator;
import org.example.currencyexchangeweb.validators.Validator;

import java.io.IOException;
import java.math.BigDecimal;

public abstract class BaseExchangeRatesServlet extends Servlet {
    private static final String PARAM_EXCHANGE_RATES_BASE_CODE = "baseCurrencyCode";
    private static final String PARAM_EXCHANGE_RATES_TARGET_CODE = "targetCurrencyCode";
    private static final String PARAM_EXCHANGE_RATES_RATE = "rate";

    private static final String URI_PARAM_EXCHANGE_RATE = "rate=";

    private final PathValidator pathValidator = PathValidator.getInstance();
    private final Validator<String> rateValidator = NumericalValidator.getInstance();
    private final Validator<RequestExchangeRateDTO> dtoValidator = ExchangeRatesValidator.getInstance();

    protected final ExchangeRatesService service = ExchangeRatesService.getInstance();

    protected final RequestExchangeRateDTO getDtoByParameters(HttpServletRequest req){
        String baseCode = req.getParameter(PARAM_EXCHANGE_RATES_BASE_CODE);
        String targetCode = req.getParameter(PARAM_EXCHANGE_RATES_TARGET_CODE);
        String rate = req.getParameter(PARAM_EXCHANGE_RATES_RATE);
        rateValidator.validate(rate);

        RequestExchangeRateDTO dto = getDto(baseCode + targetCode, new BigDecimal(rate));
        dtoValidator.validate(dto);

        return dto;
    }

    protected final String getCodesByPath(HttpServletRequest req){
        var path = req.getPathInfo();
        pathValidator.setSize(6);
        pathValidator.validate(path);

        return path.replaceAll("/", "");
    }

    protected final RequestExchangeRateDTO getDto(String codes, BigDecimal rate){
        String baseCode = codes.substring(0,3);
        String targetCode = codes.substring(3,6);

        RequestExchangeRateDTO dto;

        if(rate == null){
            dto = new RequestExchangeRateDTO(baseCode, targetCode);
        }else{
            dto = new RequestExchangeRateDTO(baseCode, targetCode, rate);
        }
        dtoValidator.validate(dto);

        return dto;
    }

    protected final BigDecimal getParameterUriRate(HttpServletRequest req) throws IOException {
        String parameterRate = req.getReader().readLine();
        if(parameterRate == null || !parameterRate.contains(URI_PARAM_EXCHANGE_RATE)){
            throw new ValidatorException("Incorrect parameter in url.");
        }
        String rate = parameterRate.replaceAll(URI_PARAM_EXCHANGE_RATE, "");
        rateValidator.validate(rate);
        return new BigDecimal(rate);
    }
}
