package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;
import org.example.currencyexchangeweb.servlets.abstracts.BaseExchangeRatesServlet;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public final class ExchangeRateServlet extends BaseExchangeRatesServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codesByPath = getCodesByPath(req);
        RequestExchangeRateDTO dto = getDto(codesByPath, null);

        ResponseExchangeRateDTO byCode = service.findByCode(dto);
        responseObject(resp, byCode);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codesByPath = getCodesByPath(req);
        BigDecimal parameterUriRate = getParameterUriRate(req);
        RequestExchangeRateDTO dto = getDto(codesByPath, parameterUriRate);

        ResponseExchangeRateDTO update = service.update(dto);
        responseObject(resp, update);
    }
}