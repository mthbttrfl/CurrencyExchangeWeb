package org.example.currencyexchangeweb.validators;

import org.example.currencyexchangeweb.dto.request.RequestExchangeRateDTO;
import org.example.currencyexchangeweb.exeptions.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public final class ExchangeRatesValidator extends DefaultValidator<RequestExchangeRateDTO>{

    private static final DefaultValidator<RequestExchangeRateDTO> INSTANCE = new ExchangeRatesValidator();
    private static final String CODES_EQUALS_MESSAGE = "Codes have the same meaning.";

    private final List<String> messages = new ArrayList<>();

    private ExchangeRatesValidator(){}

    public static DefaultValidator<RequestExchangeRateDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    protected void checkingForNull(RequestExchangeRateDTO parameter) {
        if(parameter.baseCode() == null){
            messages.add(BASE_CODE_NULL_MESSAGE);
        }
        if(parameter.targetCode() == null){
            messages.add(TARGET_CODE_NULL_MESSAGE);
        }
        if(parameter.rate() == null){
            messages.add(RATE_NULL_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    protected void checkingForSpaces(RequestExchangeRateDTO parameter) {
        if(parameter.baseCode().isBlank() && parameter.targetCode().isBlank()){
            throw new ValidatorException(ALL_BLANK_MESSAGE);
        }
        if(parameter.baseCode().isBlank()){
            messages.add(BASE_CODE_BLANK_MESSAGE);
        }
        if(parameter.targetCode().isBlank()){
            messages.add(TARGET_CODE_BLANK_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    protected void lengthCheck(RequestExchangeRateDTO parameter) {
        if(parameter.baseCode().length() != CODE_LENGTH){
            messages.add(BASE_CODE_LENGTH_MESSAGE);
        }
        if(parameter.targetCode().length() != CODE_LENGTH){
            messages.add(TARGET_CODE_LENGTH_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    protected void formatCheck(RequestExchangeRateDTO parameter) {
        if(parameter.targetCode().equals(parameter.baseCode())){
            throw new ValidatorException(CODES_EQUALS_MESSAGE);
        }
        if(!parameter.baseCode().matches(CODE_REGEX)){
            messages.add(BASE_CODE_FORMAT_MESSAGE);
        }
        if(!parameter.targetCode().matches(CODE_REGEX)){
            messages.add(TARGET_CODE_FORMAT_MESSAGE);
        }
        messagesCollector();
    }

    private void messagesCollector(){
        if(!messages.isEmpty()){
            StringBuilder message = new StringBuilder();

            for(String line : messages){
                message.append(line).append("   ");
            }

            messages.clear();

            throw new ValidatorException(message.toString());
        }
    }
}
