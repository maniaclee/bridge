package com.lvbby.bridge.api;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeException  extends Exception{
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
