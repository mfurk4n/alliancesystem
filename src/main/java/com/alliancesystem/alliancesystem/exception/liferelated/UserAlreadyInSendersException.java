package com.alliancesystem.alliancesystem.exception.liferelated;

public class UserAlreadyInSendersException extends RuntimeException{
    public UserAlreadyInSendersException() {
        super("User has already sent a gift before.");
    }
}
