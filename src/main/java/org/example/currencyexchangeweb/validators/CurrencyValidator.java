package org.example.currencyexchangeweb.validators;

import org.example.currencyexchangeweb.dto.request.RequestCurrencyDTO;
import org.example.currencyexchangeweb.exeptions.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public final class CurrencyValidator extends DefaultValidator<RequestCurrencyDTO>{

    private final static DefaultValidator<RequestCurrencyDTO> INSTANCE = new CurrencyValidator();

    private final List<String> messages = new ArrayList<>();

    private CurrencyValidator(){}

    public static DefaultValidator<RequestCurrencyDTO> getInstance(){
        return INSTANCE;
    }

    @Override
    public void checkingForNull(RequestCurrencyDTO dto){
        if (dto.code() == null) {
            messages.add(CODE_NULL_MESSAGE);
        }
        if (dto.name() == null) {
            messages.add(NAME_NULL_MESSAGE);
        }
        if (dto.sign() == null) {
            messages.add(SIGN_NULL_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    public void checkingForSpaces(RequestCurrencyDTO dto){
        if(dto.code().isBlank() && dto.name().isBlank() && dto.sign().isBlank()){
            throw new ValidatorException(ALL_BLANK_MESSAGE);
        }
        if (dto.code().isBlank()) {
            messages.add(CODE_BLANK_MESSAGE);
        }
        if (dto.name().isBlank()) {
            messages.add(NAME_BLANK_MESSAGE);
        }
        if (dto.sign().isBlank()) {
            messages.add(SIGN_BLANK_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    public void lengthCheck(RequestCurrencyDTO dto){
        if (dto.code().length() != CODE_LENGTH) {
            messages.add(CODE_LENGTH_MESSAGE);
        }
        if (dto.name().length() > NAME_LENGTH) {
            messages.add(NAME_LENGTH_MESSAGE);
        }
        if (dto.sign().length() > SIGN_LENGTH) {
            messages.add(SIGN_LENGTH_MESSAGE);
        }
        messagesCollector();
    }

    @Override
    public void formatCheck(RequestCurrencyDTO dto){
        if (!dto.code().matches(CODE_REGEX)) {
            messages.add(CODE_FORMAT_MESSAGE);
        }
        if  (!dto.name().matches(NAME_REGEX)){
            messages.add(NAME_FORMAT_MESSAGE);
        }
        messagesCollector();
    }

    private void messagesCollector(){
        if(!messages.isEmpty()){
            StringBuilder message = new StringBuilder();

            for(String line : messages){
                message.append(line);
            }

            messages.clear();

            throw new ValidatorException(message.toString());
        }
    }
}
