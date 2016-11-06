package com.lvbby.bridge.exception;

/**
 * Created by lipeng on 16/10/23.
 */
public class BridgeProcessException extends BridgeException {
    public static final String Filter = "filter";
    public static final String PreProcess = "preProcess";
    public static final String PostProcess = "PostProcess";

    private String errorType;
    private Object bridgeComponent;

    public BridgeProcessException(String message) {
        super(message);
    }

    public BridgeProcessException setErrorType(String errorType) {
        this.errorType = errorType;
        return this;
    }

    public BridgeProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeProcessException(Throwable cause) {
        super(cause);
    }

    public Object getBridgeComponent() {
        return bridgeComponent;
    }

    public BridgeProcessException setBridgeComponent(Object bridgeComponent) {
        this.bridgeComponent = bridgeComponent;
        return this;
    }
}
