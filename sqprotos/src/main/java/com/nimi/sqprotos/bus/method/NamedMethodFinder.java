package com.nimi.sqprotos.bus.method;


import com.nimi.sqprotos.bus.Bus;
import com.nimi.sqprotos.bus.MethodInfo;

import java.util.Set;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:17
 */
public class NamedMethodFinder implements MethodFinder {
    public static final String DEFAULT_NAME = "onEvent";

    private final String name;
    private final int  indexs;

    public NamedMethodFinder() {
        this(DEFAULT_NAME,-1);
    }

    public NamedMethodFinder(final String name,final int indexs) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("invalid method name");
        }
        this.name = name;
        this.indexs = indexs;
    }

    public String getName() {
        return name;
    }

    public int getIndexs() {
        return indexs;
    }

    @Override
    public Set<MethodInfo> find(final Bus bus, final Class<?> targetClass) {
        return MethodHelper.findSubscriberMethodsByName(targetClass, name);
    }
}
