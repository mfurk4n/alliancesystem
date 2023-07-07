package com.alliancesystem.alliancesystem.exception.userrelated;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User not found!");
    }
}
