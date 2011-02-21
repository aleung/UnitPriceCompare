package leoliang.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

import com.flurry.android.FlurryAgent;

public class Analytics {

    private static final String FLURRY_KEY = "5Q82B7WVG6DAIHNFF649";
    private static final String FIRST_USE_TIMESTAMP = "firstUseTimestamp";
    private static boolean isNewUser = true;
    private static Context context = null;

    /**
     * New user: no more than 24 hours since first usage
     * 
     * @param context
     * @return
     */
    private static boolean isNewUser() {
        if (!isNewUser) {
            return false;
        }
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("Analytics", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(FIRST_USE_TIMESTAMP)) {
            if (System.currentTimeMillis() - sharedPreferences.getLong(FIRST_USE_TIMESTAMP, 0) > 3600 * 24) {
                isNewUser = false;
                return false;
            }
        } else {
            sharedPreferences.edit().putLong(FIRST_USE_TIMESTAMP, System.currentTimeMillis()).commit();
        }
        return true;
    }

    public static void onEndSession(Context context) {
        Analytics.context = null;
        FlurryAgent.onEndSession(context);
    }

    public static void onEvent(String event) {
        onEvent(event, null);
    }

    public static void onEvent(String event, Map<String, String> eventParameters) {
        if (!isNewUser()) {
            FlurryAgent.onEvent(event, eventParameters);
        }
    }

    public static void onStartSession(Context context) {
        Analytics.context = context;
        FlurryAgent.setCaptureUncaughtExceptions(false);
        FlurryAgent.onStartSession(context, FLURRY_KEY);
    }

}
