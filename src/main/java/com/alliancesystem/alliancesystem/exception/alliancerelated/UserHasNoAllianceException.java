package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserHasNoAllianceException extends RuntimeException {
    public UserHasNoAllianceException() {
        super("The user is not already a member of any alliance");
    }
}
