package com.amandjaja.statistic;

public class NoConfigurationException extends RuntimeException {
    public NoConfigurationException(){
        super("Statistic Configuration not valid, please check your configuration");
    }
}
