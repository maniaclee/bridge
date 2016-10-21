package com.lvbby.bridge.exception;

/**
 * Created by peng on 16/9/22.
 */
public class BridgeParamsParsingException extends Exception{
    public BridgeParamsParsingException(String message) {
        super(message);
    }

    public BridgeParamsParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeParamsParsingException(Throwable cause) {
        super(cause);
    }
}
