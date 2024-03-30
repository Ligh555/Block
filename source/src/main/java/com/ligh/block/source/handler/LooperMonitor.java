package com.ligh.block.source.handler;

import android.util.Log;
import android.util.Printer;

import javax.inject.Inject;


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