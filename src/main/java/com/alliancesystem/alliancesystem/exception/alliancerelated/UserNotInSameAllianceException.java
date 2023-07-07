package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserNotInSameAllianceException extends RuntimeException {
    public UserNotInSameAllianceException() {
        super("The user is not in the same alliance");
    }
}
