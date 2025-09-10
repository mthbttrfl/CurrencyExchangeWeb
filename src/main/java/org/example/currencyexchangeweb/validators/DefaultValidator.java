package org.example.currencyexchangeweb.validators;

public abstract class DefaultValidator<D> implements Validator<D> {

    private static final String BASE_CURRENCY = "Base currency ";
    private static final String TARGET_CURRENCY = "Target currency ";

    protected static final String CODE_NULL_MESSAGE = "Code is required.";
    protected static final String BASE_CODE_NULL_MESSAGE = BASE_CURRENCY + CODE_NULL_MESSAGE;
    protected static final String TARGET_CODE_NULL_MESSAGE = TARGET_CURRENCY + CODE_NULL_MESSAGE;
    protected static final String NAME_NULL_MESSAGE = "Name is required.";
    protected static final String SIGN_NULL_MESSAGE = "Sign is required.";
    protected static final String RATE_NULL_MESSAGE = "Rate is required.";

    protected static final String ALL_BLANK_MESSAGE = "All parameters cannot be empty or contain only whitespace.";
    protected static final String CODE_BLANK_MESSAGE = "Code cannot be empty or contain only whitespace.";
    protected static final String BASE_CODE_BLANK_MESSAGE = BASE_CURRENCY + CODE_BLANK_MESSAGE;
    protected static final String TARGET_CODE_BLANK_MESSAGE = TARGET_CURRENCY + CODE_BLANK_MESSAGE;
    protected static final String NAME_BLANK_MESSAGE = "Name cannot be empty or contain only whitespace.";
    protected static final String SIGN_BLANK_MESSAGE = "Sign cannot be empty or contain only whitespace.";

    protected static final String CODE_LENGTH_MESSAGE = "Code must be exactly 3 characters long.";
    protected static final String BASE_CODE_LENGTH_MESSAGE = BASE_CURRENCY + CODE_LENGTH_MESSAGE;
    protected static final String TARGET_CODE_LENGTH_MESSAGE = TARGET_CURRENCY + CODE_LENGTH_MESSAGE;
    protected static final String NAME_LENGTH_MESSAGE = "Name must be between 1 and 65 characters long.";
    protected static final String SIGN_LENGTH_MESSAGE = "Sign must be between 1 and 3 characters long.";

    protected static final String CODE_FORMAT_MESSAGE = "Code must consist of 3 uppercase letters (ISO 4217 format).";
    protected static final String BASE_CODE_FORMAT_MESSAGE = BASE_CURRENCY + CODE_FORMAT_MESSAGE;
    protected static final String TARGET_CODE_FORMAT_MESSAGE = TARGET_CURRENCY + CODE_FORMAT_MESSAGE;
    protected static final String NAME_FORMAT_MESSAGE = "The name can only be in English.";

    protected static int CODE_LENGTH = 3;
    protected static int NAME_LENGTH = 65;
    protected static int SIGN_LENGTH = 3;

    protected static String CODE_REGEX = "[A-Z]{3}";
    protected static String NAME_REGEX = "^[a-zA-Z]+(\\s+[a-zA-Z]+)*$";

    @Override
    public void validate(D parameter) {
        checkingForNull(parameter);
        checkingForSpaces(parameter);
        lengthCheck(parameter);
        formatCheck(parameter);
    }

    protected abstract void checkingForNull(D parameter);
    protected abstract void checkingForSpaces(D parameter);
    protected abstract void lengthCheck(D parameter);
    protected abstract void formatCheck(D parameter);
}
