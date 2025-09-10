package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.dto.response.ResponseCurrencyDTO;
import org.example.currencyexchangeweb.servlets.abstracts.BaseCurrenciesServlet;

import java.io.IOException;

@WebServlet("/currency/*")
public final class CurrencyServlet extends BaseCurrenciesServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String codeByPath = getCodeByPath(req);
        RequestCurrencyDTO dto = getDtoByCode(codeByPath);

        ResponseCurrencyDTO byCode = service.findByCode(dto);
        responseObject(resp, byCode);
    }
}
