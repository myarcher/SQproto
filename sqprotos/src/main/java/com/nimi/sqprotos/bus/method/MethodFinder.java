package com.nimi.sqprotos.bus.method;


import com.nimi.sqprotos.bus.Bus;
import com.nimi.sqprotos.bus.MethodInfo;

import java.util.Set;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:14
 */
public interface MethodFinder {

    Set<MethodInfo> find(final Bus bus, final Class<?> targetClass);
}
