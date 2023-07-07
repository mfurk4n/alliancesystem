package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class UserInvalidAuthorizationException extends RuntimeException {
    public UserInvalidAuthorizationException() {
        super("User don't have the necessary authority");
    }
}
