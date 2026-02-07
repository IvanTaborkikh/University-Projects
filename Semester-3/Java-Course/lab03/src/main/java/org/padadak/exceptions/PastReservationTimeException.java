package org.padadak.exceptions;

public class PastReservationTimeException extends Exception {
    public PastReservationTimeException(String msg) {
        super(msg);
    }
}
