package com.lvbby.bridge.api;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    Object proxy(Context context) throws BridgeException;
}
