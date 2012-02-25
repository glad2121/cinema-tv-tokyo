package com.twitter.cinema_tv_tokyo.common.exception;

public class CinemaTvTokyoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CinemaTvTokyoException() {
    }

    public CinemaTvTokyoException(String message) {
        super(message);
    }

    public CinemaTvTokyoException(Throwable cause) {
        super(cause);
    }

    public CinemaTvTokyoException(String message, Throwable cause) {
        super(message, cause);
    }

}
