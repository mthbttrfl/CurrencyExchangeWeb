package org.example.currencyexchangeweb.validators;

import org.example.currencyexchangeweb.exeptions.ValidatorException;

public final class PathValidator implements Validator<String> {

    private static final PathValidator INSTANCE = new PathValidator();

    private static final String PATH_MESSAGE = "Incorrect page address.";
    private static final String FORMAT_MESSAGE = "Incorrect format.";

    private static int size = 3;

    private PathValidator(){};

    public static PathValidator getInstance(){
        return INSTANCE;
    }

    @Override
    public void validate(String path) {
        try {
            path = path.replaceAll("/","");

            if(path.length() != size){
                throw new ValidatorException(FORMAT_MESSAGE);
            }

        }catch (ValidatorException ex){
            throw new ValidatorException(ex.getMessage());
        }
        catch (Exception ex){
            throw new ValidatorException(PATH_MESSAGE);
        }
    }

    public void setSize(int size) {
        if(size > 0){
            PathValidator.size = size;
        }
    }
}
