package com.alliancesystem.alliancesystem.exception.userrelated;

public class UserNotEnoughCoinException extends RuntimeException{
    public UserNotEnoughCoinException() {
        super("Not enough coin!");
    }
}
