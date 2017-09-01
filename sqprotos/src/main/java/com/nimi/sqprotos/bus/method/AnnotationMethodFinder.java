package com.nimi.sqprotos.bus.method;


import com.nimi.sqprotos.bus.Bus;
import com.nimi.sqprotos.bus.MethodInfo;

import java.util.Set;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:15
 */
public class AnnotationMethodFinder implements MethodFinder {


    @Override
    public Set<MethodInfo> find(final Bus bus, final Class<?> targetClass) {
        return MethodHelper.findSubscriberMethodsByAnnotation(targetClass);
    }
}
