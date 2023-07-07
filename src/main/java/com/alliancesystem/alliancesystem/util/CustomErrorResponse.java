package com.alliancesystem.alliancesystem.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Getter
@Setter
public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private HttpStatus status;
    private String message;

    private CustomErrorResponse() {
    }

    private CustomErrorResponse(HttpStatus status, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
    }

    public static ResponseEntity<CustomErrorResponse> sendError(HttpStatus status, String message) {
        return new ResponseEntity<>(new CustomErrorResponse(status, message), status);
    }


}
