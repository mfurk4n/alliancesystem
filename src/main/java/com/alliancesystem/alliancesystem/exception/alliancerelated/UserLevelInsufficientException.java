package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserLevelInsufficientException extends RuntimeException {
    public UserLevelInsufficientException() {
        super("The minimum required level for the alliance cannot be higher than the current user level");
    }
}
