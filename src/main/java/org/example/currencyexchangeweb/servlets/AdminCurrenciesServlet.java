package org.example.currencyexchangeweb.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currencyexchangeweb.dto.CurrencyDTO;
import org.example.currencyexchangeweb.servies.AdminCurrenciesService;
import org.example.currencyexchangeweb.servies.interfacies.AdminService;
import org.example.currencyexchangeweb.servlets.abstracts.AdminServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/currencies")
public final class AdminCurrenciesServlet extends AdminServlet {

    private final AdminService<Long, CurrencyDTO> service = AdminCurrenciesService.getInstance();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        checkAdmin(req);
        Long parameterId = getParameterId(req);
        boolean delete = service.delete(parameterId);
        responseObject(resp, delete);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        checkAdmin(req);
        List<CurrencyDTO> all = service.findAll();
        responseObject(resp, all);
    }
}