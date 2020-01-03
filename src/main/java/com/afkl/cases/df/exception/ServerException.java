package com.afkl.cases.df.exception;

public class ServerException extends RuntimeException {

    public ServerException(final String message, final Throwable e) {
        super(message, e);
    }
}
