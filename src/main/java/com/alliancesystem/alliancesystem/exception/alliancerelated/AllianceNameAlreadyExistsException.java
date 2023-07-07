package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceNameAlreadyExistsException extends RuntimeException {
    public AllianceNameAlreadyExistsException() {
        super("Alliance name is already in use");
    }
}
