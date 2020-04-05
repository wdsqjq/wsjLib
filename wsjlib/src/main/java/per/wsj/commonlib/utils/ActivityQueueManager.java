package per.wsj.commonlib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by shiju.wang on 2017/9/16.
 */

public class ActivityQueueManager {

    private static final ActivityQueueManager mInstance = new ActivityQueueManager();

    private final static String TAG = ActivityQueueManager.class.getSimpleName();
    private static LinkedList<Activity> mQueue;

    private ActivityQueueManager() {
        ActivityQueueManager.mQueue = new LinkedList<Activity>();
    }

    /**
     * push activity to queue
     *
     * @param activity
     * @return void
     * @throws
     */
    public void pushActivity(Activity activity) {
        mInstance.doPushActivity(activity);
        Log.v(TAG, "pushActivity size=" + mQueue.size() + " name=" + activity.getLocalClassName());
    }

    /**
     * pop activity from queue
     *
     * @param activity
     * @return void
     * @throws
     */
    public void popActivity(Activity activity) {
        mInstance.doPopActivity(activity);
        Log.v(TAG, "popActivity size=" + mQueue.size() + " name=" + activity.getLocalClassName());
    }

    /**
     * pop the stack top activity
     *
     * @return Activity
     * @throws
     */
    public Activity pop() {
        if (mQueue != null && mQueue.size() > 0) {
            return mQueue.peek();
        } else {
            return null;
        }
    }

    /**
     * pop the postion activity
     *
     * @return Activity
     * @throws
     */
    public Activity popIndex(int postion) {
        if (mQueue != null && mQueue.size() > postion) {
            return mQueue.get(postion);
        } else {
            return null;
        }
    }

    /**
     * finish all activity from queue
     *
     * @return void
     * @throws
     */
    public void finishAllActivity() {
        mInstance.doFinishAll();
    }

    /**
     * finish all activity from queue except current
     *
     * @return void
     * @throws
     */
    public void finishAllOtherActivity(Activity activity) {
        Iterator<Activity> it = mQueue.iterator();
        while (it.hasNext()) {
            Activity a = it.next();
            if (!activity.getLocalClassName().equals(a.getLocalClassName())) {
                it.remove();
                a.finish();
            }
        }
    }

    @SuppressLint("NewApi")
    public void doPushActivity(Activity activity) {
        // 解决系统2.2版本的bug
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            mQueue.push(activity);
        } else {
            mQueue.addFirst(activity);
        }
    }

    public void doPopActivity(Activity activity) {
        ActivityQueueManager.mQueue.remove(activity);
    }

    public void doFinishAll() {
        Iterator<Activity> it = mQueue.iterator();
        while (it.hasNext()) {
            Activity a = it.next();
            it.remove();
            a.finish();
        }
    }

    public static ActivityQueueManager getInstance() {
        return mInstance;
    }

    public LinkedList<Activity> getActivityLinkQueue() {
        return mQueue;
    }

    public int getSize() {
        return mQueue.size();
    }


    /**
     * 关闭N个activities
     * @param closeNumberActivities 关闭activity的个数
     */
    public void closeNumberActivities(int closeNumberActivities) {
        // 关闭个数小于1的时候直接跳出
        if (closeNumberActivities <= 0) {
            return;
        }
        LinkedList<Activity> mActivities = mQueue;
        if (mActivities != null && mActivities.size() <= 1) {
            return;
        }

        try {
            int countTemp = 0;
            // 倒序遍历acitivty
            for (int i = mActivities.size() - 1; i >= 0; i--) {
                // 如果当前页面为NativeAppActivity，则直接finish();
                Activity mActivity = mActivities.get(i);
                if (mActivity != null ) {
                    mActivity.finish();
                    mActivities.remove(mActivity);
                }
                // 其他情况下finish掉activity
                else {
                    // 当前页面不能是最后一页
                    if (mActivities.size() > 1 ) {
                        mActivity.finish();
                        mActivities.remove(mActivity);
                        countTemp ++;
                    } else {
                        i = -1;
                    }
                }
                // 退出循环
                if (countTemp == closeNumberActivities) {
                    i = -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
