package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.request.RequestExchangeDTO;
import org.example.currencyexchangeweb.dto.response.ResponseExchangeDTO;
import org.example.currencyexchangeweb.servlets.abstracts.BaseExchangeServlet;

import java.io.IOException;

@WebServlet("/exchange")
public final class ExchangeServlet extends BaseExchangeServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestExchangeDTO dtoByParameters = getDtoByParameters(req);

        ResponseExchangeDTO exchange = service.exchange(dtoByParameters);
        responseObject(resp, exchange);
    }
}
