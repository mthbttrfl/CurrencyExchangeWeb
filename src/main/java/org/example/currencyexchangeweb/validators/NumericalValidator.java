package org.example.currencyexchangeweb.validators;

import org.example.currencyexchangeweb.exeptions.ValidatorException;

import java.math.BigDecimal;

public final class NumericalValidator implements Validator<String>{

    private static final Validator<String> INSTANCE = new NumericalValidator();

    private static final String RATE_FORMAT = "9999999999.999999";
    private static final String RATE_MESSAGE_FORMAT = "Incorrect format.";
    private static final String RATE_MESSAGE_NEGATIVE = "Cannot be negative.";
    private static final String RATE_MESSAGE_SCALE = "Incorrect fractional part format.";
    private static final String RATE_MASSAGE_MAX = "Maximum value exceeded.";
    private static final String RATE_MASSAGE_ZERO = "Cannot be zero.";
    private static final String RATE_MASSAGE_SPACES = "Remove spaces.";

    private NumericalValidator(){}

    public static Validator<String> getInstance(){
        return INSTANCE;
    }

    @Override
    public void validate(String rate) {
        try {
            if(rate.matches(RATE_FORMAT)){
                throw new ValidatorException(RATE_MASSAGE_SPACES);
            }

            BigDecimal bigDecimal = new BigDecimal(rate).stripTrailingZeros();

            if(bigDecimal.compareTo(BigDecimal.ZERO) == 0){
                throw new ValidatorException(RATE_MASSAGE_ZERO);
            }
            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidatorException(RATE_MESSAGE_NEGATIVE);
            }
            if (bigDecimal.scale() > 6) {
                throw new ValidatorException(RATE_MESSAGE_SCALE);
            }
            if (bigDecimal.compareTo(new BigDecimal(RATE_FORMAT)) > 0) {
                throw new ValidatorException(RATE_MASSAGE_MAX);
            }

        }catch (ValidatorException ex){
            throw new ValidatorException(ex.getMessage());
        }
        catch (Exception ex) {
            throw new ValidatorException(RATE_MESSAGE_FORMAT);
        }
    }
}
