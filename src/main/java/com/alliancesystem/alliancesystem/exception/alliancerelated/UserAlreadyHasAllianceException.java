package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserAlreadyHasAllianceException extends RuntimeException {
    public UserAlreadyHasAllianceException() {
        super("User is already a member of an alliance");
    }
}
