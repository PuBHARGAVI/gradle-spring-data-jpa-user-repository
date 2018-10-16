package com.hxs.web.model.reponse;

import java.time.Instant;
import java.util.Date;

/**
 * @author HSteidel
 *
 *  Very generic API JSON response message for cases when a response entity is not
 *  really needed but it's just super nice to send something meaningful back.
 */
public class ApiResponseMessage {

    private String message;

    private Date date = Date.from(Instant.now());;

    public ApiResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
