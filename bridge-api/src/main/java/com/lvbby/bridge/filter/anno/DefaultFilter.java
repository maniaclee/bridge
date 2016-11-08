package com.lvbby.bridge.filter.anno;

import com.lvbby.bridge.annotation.Filter;
import com.lvbby.bridge.gateway.Context;

/**
 * Created by lipeng on 16/10/28.
 */
public class DefaultFilter extends AnnotationFilter<Filter> {
    @Override
    public boolean canVisit(Context context, Filter filter) {
        return filter.pass();
    }

}
