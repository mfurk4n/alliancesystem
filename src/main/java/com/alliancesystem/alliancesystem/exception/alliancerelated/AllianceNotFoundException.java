package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceNotFoundException extends RuntimeException {
    public AllianceNotFoundException(){
        super("Alliance not found!");
    }
}
