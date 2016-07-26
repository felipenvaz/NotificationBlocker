package com.enterprises.fnv.notificationblocker;

/**
 * Created by FELIPE on 16/12/2015.
 */
public class NotificationApp {
    private long id;
    private String mainText;
    private String subText;
    private String title;
    private String _package;
    private boolean removed;

    public NotificationApp(){
        this.id = -1;
        this.subText = "";
        this.mainText = "";
        this.title = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
