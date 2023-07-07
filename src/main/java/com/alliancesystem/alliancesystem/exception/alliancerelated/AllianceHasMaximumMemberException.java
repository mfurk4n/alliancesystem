package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceHasMaximumMemberException extends RuntimeException {
    public AllianceHasMaximumMemberException() {
        super("The alliance has reached its maximum number of members");
    }
}
