package com.alliancesystem.alliancesystem.exception.alliancerelated;

public class AllianceLeaderLeaveException extends RuntimeException {
    public AllianceLeaderLeaveException() {
        super("User cannot leave while being the leader of the alliance");
    }
}
