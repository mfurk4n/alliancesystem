package com.alliancesystem.alliancesystem.exception.liferelated;

public class InsufficientTimeElapsedException extends RuntimeException {
    public InsufficientTimeElapsedException() {
        super("Not enough time has passed since the last request.");
    }
}
