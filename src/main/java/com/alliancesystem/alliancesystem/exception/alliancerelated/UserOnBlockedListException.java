package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserOnBlockedListException extends RuntimeException {
    public UserOnBlockedListException() {
        super("Blocked players cannot join the alliance");
    }
}
