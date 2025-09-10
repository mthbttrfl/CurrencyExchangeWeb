package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeRateDTO;
import org.example.currencyexchangeweb.servlets.abstracts.BaseExchangeRatesServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public final class ExchangeRatesServlet extends BaseExchangeRatesServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ResponseExchangeRateDTO> findAll = service.findAll();
        responseObject(resp, findAll);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestExchangeRateDTO dtoByParameters = getDtoByParameters(req);

        ResponseExchangeRateDTO save = service.save(dtoByParameters);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        responseObject(resp, save);
    }
}
