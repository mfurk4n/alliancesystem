package com.alliancesystem.alliancesystem.exception.liferelated;

public class UserMaximumLifeException extends RuntimeException {
    public UserMaximumLifeException() {
        super("User lifes are already full");
    }
}
