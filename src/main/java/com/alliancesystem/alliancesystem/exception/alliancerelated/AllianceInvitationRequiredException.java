package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceInvitationRequiredException extends RuntimeException {
    public AllianceInvitationRequiredException() {
        super("The alliance can only accept members by invitation");
    }
}
