package com.enterprises.fnv.notificationblocker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by FELIPE on 27/09/2015.
 */
public class Preferences {
    private static String PREFS_NAME = "notification_blocker";
    private static String NOTIFICATION_ACCESS = "block_on";

    public static void setNotificationAccessOn (Context context, boolean enabled){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(NOTIFICATION_ACCESS, enabled);
        editor.commit();
    }
    public static boolean getBlockOn (Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(NOTIFICATION_ACCESS, false);
    }
}
