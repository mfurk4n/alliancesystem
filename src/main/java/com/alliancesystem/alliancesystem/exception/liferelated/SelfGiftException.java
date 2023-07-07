package com.alliancesystem.alliancesystem.exception.liferelated;

public class SelfGiftException extends RuntimeException {
    public SelfGiftException() {
        super("You cannot send a life gift to yourself.");
    }
}
