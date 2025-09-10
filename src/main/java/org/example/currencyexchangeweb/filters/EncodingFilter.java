package org.example.currencyexchangeweb.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public final class EncodingFilter implements Filter {

    private static final String CHARACTER_ENCODING_UTF = "UTF-8";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_CSS = "text/css";
    private static final String CONTENT_TYPE_JAVASCRIPT = "application/javascript";
    private static final String CONTENT_TYPE_HTML = "text/html";

    private static final String CURRENCIES_PATH = "/currencies";
    private static final String CURRENCY_PATH = "/currency";
    private static final String EXCHANGE_RATES_PATH = "/exchangeRates";
    private static final String EXCHANGE_RATE_PATH = "/exchangeRate";
    private static final String EXCHANGE_PATH = "/exchange";
    private static final String ADMIN_PATH = "/admin";

    private static final String CSS_EXTENSION = ".css";
    private static final String JS_EXTENSION = ".js";
    private static final String HTML_EXTENSION = ".html";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        servletRequest.setCharacterEncoding(CHARACTER_ENCODING_UTF);
        servletResponse.setCharacterEncoding(CHARACTER_ENCODING_UTF);

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();

        if (isApiPath(path)) {
            servletResponse.setContentType(CONTENT_TYPE_JSON);
        } else if (path.endsWith(CSS_EXTENSION)) {
            servletResponse.setContentType(CONTENT_TYPE_CSS);
        } else if (path.endsWith(JS_EXTENSION)) {
            servletResponse.setContentType(CONTENT_TYPE_JAVASCRIPT);
        } else if (path.endsWith(HTML_EXTENSION)) {
            servletResponse.setContentType(CONTENT_TYPE_HTML);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isApiPath(String path) {
        return path.startsWith(CURRENCIES_PATH) ||
                path.startsWith(EXCHANGE_RATES_PATH) ||
                path.startsWith(EXCHANGE_RATE_PATH) ||
                path.startsWith(EXCHANGE_PATH) ||
                path.startsWith(CURRENCY_PATH) ||
                path.startsWith(ADMIN_PATH);
    }
}