package com.itsol.train.mock.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {
    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
