package org.example.currencyexchangeweb.utils;


import org.example.currencyexchangeweb.exeptions.PropertiesException;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private static final String MESSAGE = "Error loading property file, server error.";

    private static final String PROPERTIES_NAME = "application.properties";

    private PropertiesUtil(){};

    static {
        loadProperties();
    }

    public static String get (String key){
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties(){
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME)){
            PROPERTIES.load(inputStream);
        }catch (IOException ex){
            throw new PropertiesException(MESSAGE);
        }
    }
}