package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserSelfRemoveAllianceException extends RuntimeException {
    public UserSelfRemoveAllianceException() {
        super("User cannot remove yourself from the alliance");
    }
}
