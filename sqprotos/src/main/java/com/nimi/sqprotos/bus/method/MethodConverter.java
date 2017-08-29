package com.nimi.sqprotos.bus.method;

import com.nimi.sqprotos.bus.MethodInfo;

import java.lang.reflect.Method;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:32
 */
interface MethodConverter {

    MethodInfo convert(final Method method);
}
