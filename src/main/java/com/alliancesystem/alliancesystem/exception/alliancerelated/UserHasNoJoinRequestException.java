package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserHasNoJoinRequestException extends RuntimeException {
    public UserHasNoJoinRequestException() {
        super("No existing request found");
    }
}
