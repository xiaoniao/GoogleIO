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
 * ContentObserver��������ʱ���������ĸı�Ϊһ�������ThrottledContentObserver���Է�ֹ��������ĸı�ʱ�䷵��Ϊ�����
 * 
 * һ�仰�����������þ��ǰѶ�ʱ���������ı���Ѽ�������ֻ���ؽ��һ�Ρ� ��ֹƵ���ķ������ݡ�
 * 
 */
public class ThrottledContentObserver extends ContentObserver {

    Handler                  mMyHandler;
    Runnable                 mScheduledRun  = null;

    private static final int THROTTLE_DELAY = 1000;

    Callbacks                mCallback      = null;

    // �Զ���ӿ�,������
    public interface Callbacks {
        public void onThrottledContentObserverFired();
    }

    // ���췽��
    public ThrottledContentObserver(Callbacks callback) {
        super(null);
        mMyHandler = new Handler(); // ʵ����Handler
        mCallback = callback; // ���ݼ�����ʵ��
    }

    @Override
    public void onChange(boolean selfChange) {

        if (mScheduledRun != null) {
            mMyHandler.removeCallbacks(mScheduledRun); // ��Handler���Ƴ�Runnable
        } else {
            // ʵ�����߳�
            mScheduledRun = new Runnable() {
                @Override
                public void run() {
                    if (mCallback != null) {
                        mCallback.onThrottledContentObserverFired();
                    }
                }
            };
        }

        // Handler�ӳ�ִ���߳�
        mMyHandler.postDelayed(mScheduledRun, THROTTLE_DELAY);
    }

    public void cancelPendingCallback() {
        if (mScheduledRun != null) {
            mMyHandler.removeCallbacks(mScheduledRun);// ��Handler���Ƴ�Runnable
        }
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        onChange(selfChange);
    }
}
