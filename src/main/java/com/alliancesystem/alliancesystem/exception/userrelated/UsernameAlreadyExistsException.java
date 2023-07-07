package com.alliancesystem.alliancesystem.exception.userrelated;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Username is already in use");
    }
}
