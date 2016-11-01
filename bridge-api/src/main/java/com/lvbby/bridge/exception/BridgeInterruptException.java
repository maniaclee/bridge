package com.lvbby.bridge.exception;

/**
 * Created by lipeng on 16/11/1.
 */
public class BridgeInterruptException extends RuntimeException {
    private Object arg;

    public BridgeInterruptException(String message) {
        super(message);
    }

    public BridgeInterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeInterruptException(Throwable cause) {
        super(cause);
    }

    public BridgeInterruptException setArg(Object arg) {
        this.arg = arg;
        return this;
    }

    public Object getArg() {
        return arg;
    }
}
