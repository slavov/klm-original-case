package com.afkl.cases.df.exception;

public class ClientException extends RuntimeException {

    public ClientException(final String message, final Throwable e) {
        super(message, e);
    }
}
