package com.amandjaja.statistic;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(){
        super("Statistic Data not valid, please check your data");
    }
}
