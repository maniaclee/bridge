package com.lvbby.bridge.exception;

/**
 * Created by lipeng on 16/10/23.
 */
public class BridgeRoutingException extends Exception {
    public BridgeRoutingException(String message) {
        super(message);
    }

    public BridgeRoutingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeRoutingException(Throwable cause) {
        super(cause);
    }

    public BridgeRoutingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
