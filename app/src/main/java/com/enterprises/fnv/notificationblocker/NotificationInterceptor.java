package com.enterprises.fnv.notificationblocker;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.enterprises.fnv.notificationblocker.DAO.FilterDAO;
import com.enterprises.fnv.notificationblocker.DAO.NotificationDAO;
import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.MDL.FilterItem;

import java.util.List;

/**
 * Created by FELIPE on 13/12/2015.
 */
public class NotificationInterceptor extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        final String packageName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;

        NotificationApp notificationApp = new NotificationApp();
        notificationApp.setTitle(extras.getString(Notification.EXTRA_TITLE, ""));
        notificationApp.setMainText(extras.getString(Notification.EXTRA_TEXT, ""));
        notificationApp.setPackage(packageName);
        notificationApp.setSubText(extras.getString(Notification.EXTRA_SUB_TEXT, ""));

        FilterDAO filterDAO = new FilterDAO(this);
        List<Filter> filters = filterDAO.getAllFilters(packageName, true);
        for(Filter filter:filters){
            if(notificationShouldBeCancelled(filter, notificationApp)){
                cancelNotification(sbn.getKey());
                notificationApp.setRemoved(true);
                break;
            }
        }

        NotificationDAO dao = new NotificationDAO(this);
        dao.create(notificationApp);
    }

    private boolean notificationShouldBeCancelled (Filter filter, NotificationApp notificationApp){
        for(FilterItem item : filter.getItems()){
            switch (item.getFilterAt()){
                case Any:
                    if(Utils.stringContainsOther(notificationApp.getTitle(), item.getFilterText())
                            || Utils.stringContainsOther(notificationApp.getMainText(), item.getFilterText())
                            || Utils.stringContainsOther(notificationApp.getSubText(), item.getFilterText())){
                        return true;
                    }
                    break;
                case Text:
                    if(Utils.stringContainsOther(notificationApp.getMainText(), item.getFilterText())){
                        return true;
                    }
                    break;
                case SubText:
                    if(Utils.stringContainsOther(notificationApp.getSubText(), item.getFilterText())){
                        return true;
                    }
                    break;
                case Title:
                    if(Utils.stringContainsOther(notificationApp.getTitle(), item.getFilterText())){
                        return true;
                    }
                    break;
            }
        }

        return false;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Nothing to do
    }

    public static boolean isNotificationAccessEnabled = false;

    @Override
    public IBinder onBind(Intent mIntent) {
        IBinder mIBinder = super.onBind(mIntent);
        isNotificationAccessEnabled = true;
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent mIntent) {
        boolean mOnUnbind = super.onUnbind(mIntent);
        isNotificationAccessEnabled = false;
        return mOnUnbind;
    }

}