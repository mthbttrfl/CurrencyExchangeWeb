package org.example.currencyexchangeweb.validators;

import org.example.currencyexchangeweb.exeptions.ValidatorException;
import org.example.currencyexchangeweb.utils.PropertiesUtil;

public final class AdminUserValidator implements Validator<String>{

    private static final Validator<String> INSTANCE = new AdminUserValidator();

    private static final String ADMIN_HASH_NAME = "db.admin.hash";
    private static final long ADMIN_HASH_CODE;

    private static final String MESSAGE = "Access denied.";

    private AdminUserValidator(){}

    static {
        try {
            ADMIN_HASH_CODE = Long.parseLong(PropertiesUtil.get(ADMIN_HASH_NAME));
        }catch (Exception ex){
            throw new ValidatorException(MESSAGE);
        }
    }

    public static Validator<String> getInstance(){
        return INSTANCE;
    }

    @Override
    public void validate(String parameter) {
        if(parameter.hashCode() != ADMIN_HASH_CODE){
            throw new ValidatorException(MESSAGE);
        }
    }
}
