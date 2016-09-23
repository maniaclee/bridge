package com.lvbby.bridge.api.gateway;

import com.lvbby.bridge.api.exception.BridgeException;
import com.lvbby.bridge.api.wrapper.Context;

/**
 * Created by peng on 16/9/22.
 */
public interface ApiGateWay {

    Object proxy(Context context) throws BridgeException;
}
