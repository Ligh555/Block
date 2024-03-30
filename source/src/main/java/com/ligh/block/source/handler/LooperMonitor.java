package com.ligh.block.source.handler;

import android.util.Log;
import android.util.Printer;

import javax.inject.Inject;

/**
 * 原理基于 Looper.loop()
 * public static void loop() {
 *     ...
 *
 *     for (;;) {
 *         ...
 *
 *         // This must be in a local variable, in case a UI event sets the logger
 *         Printer logging = me.mLogging;
 *         if (logging != null) {
 *             logging.println(">>>>> Dispatching to " + msg.target + " " +
 *                     msg.callback + ": " + msg.what);
 *         }
 *
 *         msg.target.dispatchMessage(msg);
 *
 *         if (logging != null) {
 *             logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
 *         }
 *
 *         ...
 *     }
 * }
 */


public class LooperMonitor implements Printer {

    private String TAG = "LooperMonitor";


    private boolean mStartedPrinting = false;
    private long mBlockThresholdMillis = 10;

    private long mStartTimeMillis = 0L;

    @Inject
    public LooperMonitor() {
    }

    @Override
    public void println(String x) {
        if (!mStartedPrinting) {
            mStartTimeMillis = System.currentTimeMillis();
            mStartedPrinting = true;
        } else {
            final long endTime = System.currentTimeMillis();
            mStartedPrinting = false;
            if (isBlock(endTime)) {
                notifyBlockEvent(x, endTime - mStartTimeMillis);
            }
        }
    }

    private void notifyBlockEvent(String x, long spaceTime) {
        Log.i(TAG, "notifyBlockEvent: " + x + "    " + spaceTime);
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTimeMillis > mBlockThresholdMillis;
    }
}