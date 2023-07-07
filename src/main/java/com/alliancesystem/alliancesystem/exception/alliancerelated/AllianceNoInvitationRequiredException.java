package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceNoInvitationRequiredException extends RuntimeException {
    public AllianceNoInvitationRequiredException() {
        super("The alliance is already closed for invitation-based membership");
    }
}
