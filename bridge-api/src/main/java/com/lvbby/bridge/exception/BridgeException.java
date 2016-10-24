package com.lvbby.bridge.exception;

/**
 * Created by peng on 16/9/22.
 * base Exception
 */
public class BridgeException extends Exception {
    public BridgeException(String message) {
        super(message);
    }

    public BridgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeException(Throwable cause) {
        super(cause);
    }
}
