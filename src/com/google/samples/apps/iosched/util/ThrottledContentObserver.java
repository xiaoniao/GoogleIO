package com.google.samples.apps.iosched.util;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * A ContentObserver that bundles multiple consecutive changes in a short time period into one.
 * 
 * This can be used in place of a regular ContentObserver to protect against getting too many consecutive change events
 * as a result of data changes.
 * 
 * This observer will wait a while before firing, so if multiple requests come in in quick succession, they will cause
 * it to fire only once.
 * 
 * ContentObserver捆绑多个短时间内连续的改变为一个。这个ThrottledContentObserver可以防止多个连续的改变时间返回为结果。
 * 
 * 一句话。这个类的作用就是把短时间内连续改变的搜集起来，只返回结果一次。 防止频繁的返回数据。
 * 
 */
public class ThrottledContentObserver extends ContentObserver {

    Handler                  mMyHandler;
    Runnable                 mScheduledRun  = null;

    private static final int THROTTLE_DELAY = 1000;

    Callbacks                mCallback      = null;

    // 自定义接口,监听器
    public interface Callbacks {
        public void onThrottledContentObserverFired();
    }

    // 构造方法
    public ThrottledContentObserver(Callbacks callback) {
        super(null);
        mMyHandler = new Handler(); // 实例化Handler
        mCallback = callback; // 传递监听器实例
    }

    @Override
    public void onChange(boolean selfChange) {

        if (mScheduledRun != null) {
            mMyHandler.removeCallbacks(mScheduledRun); // 从Handler中移除Runnable
        } else {
            // 实例化线程
            mScheduledRun = new Runnable() {
                @Override
                public void run() {
                    if (mCallback != null) {
                        mCallback.onThrottledContentObserverFired();
                    }
                }
            };
        }

        // Handler延迟执行线程
        mMyHandler.postDelayed(mScheduledRun, THROTTLE_DELAY);
    }

    public void cancelPendingCallback() {
        if (mScheduledRun != null) {
            mMyHandler.removeCallbacks(mScheduledRun);// 从Handler中移除Runnable
        }
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        onChange(selfChange);
    }
}
