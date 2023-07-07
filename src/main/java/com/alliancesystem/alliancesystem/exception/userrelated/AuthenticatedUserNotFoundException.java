package com.alliancesystem.alliancesystem.exception.userrelated;

public class AuthenticatedUserNotFoundException extends RuntimeException {
    public AuthenticatedUserNotFoundException() {
        super("Session Not Found");
    }


}
