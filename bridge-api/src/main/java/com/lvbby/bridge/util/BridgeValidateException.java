package com.lvbby.bridge.util;

/**
 * Created by lipeng on 16/11/2.
 */
public class BridgeValidateException extends IllegalArgumentException {
    public BridgeValidateException() {
    }

    public BridgeValidateException(String s) {
        super(s);
    }

    public BridgeValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeValidateException(Throwable cause) {
        super(cause);
    }
}
