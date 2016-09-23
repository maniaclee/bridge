package com.lvbby.bridge.api.exception;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeRunTimeException extends RuntimeException {
    public BridgeRunTimeException(String message) {
        super(message);
    }

    public BridgeRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeRunTimeException(Throwable cause) {
        super(cause);
    }
}
