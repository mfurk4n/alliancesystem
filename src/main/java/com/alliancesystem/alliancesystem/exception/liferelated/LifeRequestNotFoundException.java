package com.alliancesystem.alliancesystem.exception.liferelated;

public class LifeRequestNotFoundException extends RuntimeException {
    public LifeRequestNotFoundException(){
        super("Life request not found!");
    }
}
