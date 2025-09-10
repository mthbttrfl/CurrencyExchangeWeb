package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.dto.response.ResponseCurrencyDTO;
import org.example.currencyexchangeweb.servlets.abstracts.BaseCurrenciesServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public final class CurrenciesServlet extends BaseCurrenciesServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ResponseCurrencyDTO> findAll = service.findAll();
        responseObject(resp, findAll);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestCurrencyDTO dtoByParameters = getDtoByParameters(req);

        ResponseCurrencyDTO save = service.save(dtoByParameters);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        responseObject(resp, save);
    }
}