package com.alliancesystem.alliancesystem.exception.liferelated;

public class UserMaximumGiftedLifeException extends RuntimeException {
    public UserMaximumGiftedLifeException() {
        super("User cannot request more than 10 lives.");
    }
}
