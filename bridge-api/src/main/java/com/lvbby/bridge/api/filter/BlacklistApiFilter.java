package com.lvbby.bridge.api.filter;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.lvbby.bridge.api.gateway.ApiFilter;
import com.lvbby.bridge.api.gateway.Context;

/**
 * Created by lipeng on 16/10/19.
 */
public class BlacklistApiFilter implements ApiFilter {
    private Multimap<String, String> map = LinkedListMultimap.create();

    public void blockApi(String service, String method) {

    }

    @Override
    public boolean canVisit(Context context) {
        return false;
    }
}
