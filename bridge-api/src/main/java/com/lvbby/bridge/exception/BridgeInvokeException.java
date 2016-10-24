package com.lvbby.bridge.exception;

/**
 * Created by lipeng on 16/10/23.
 */
public class BridgeInvokeException extends BridgeException {
    public BridgeInvokeException(String message) {
        super(message);
    }

    public BridgeInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeInvokeException(Throwable cause) {
        super(cause);
    }
}
