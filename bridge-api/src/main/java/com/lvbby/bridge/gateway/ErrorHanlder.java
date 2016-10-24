package com.lvbby.bridge.gateway;

/**
 * Created by lipeng on 16/10/24.
 */
public interface ErrorHanlder {

    Object handleError(Context context, Exception e);
}
