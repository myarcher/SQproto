package com.nimi.sqprotos.bus.scheduler;


import com.nimi.sqprotos.bus.Bus;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 16:00
 */
class SenderScheduler implements Scheduler {
    private Bus mBus;

    public SenderScheduler(final Bus bus) {
        mBus = bus;
    }

    @Override
    public void post(final Runnable runnable) {
        runnable.run();
    }
}
