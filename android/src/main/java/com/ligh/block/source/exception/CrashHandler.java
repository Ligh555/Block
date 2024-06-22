package com.ligh.block.source.exception;

import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static void init(Context context) {
        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (exceptionHandler != null
                && exceptionHandler.getClass().getName().startsWith("com.ligh.block.source.exception")) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context));
    }

    private Context mContext;

    private CrashHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //128kb-1
        int maxStackTraceSize = 131071;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTraceString = sw.toString();

        if (stackTraceString.length() > maxStackTraceSize) {
            String disclaimer = " [stack trace too large]";
            stackTraceString = stackTraceString.substring(0, maxStackTraceSize - disclaimer.length()) + disclaimer;
        }

        // 在另外一个进程 打开Activity
        Intent intent = new Intent(mContext.getApplicationContext(), ExceptionActivity.class);
        intent.putExtra(ExceptionActivity.CRASH_MESSEAG, stackTraceString);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}
