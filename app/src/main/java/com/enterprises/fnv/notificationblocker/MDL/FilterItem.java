package com.enterprises.fnv.notificationblocker.MDL;

/**
 * Created by fnv on 22/12/2015.
 */
public class FilterItem {
    private String filterText;
    private EFilterAt filterAt;
    private int position;
    private long id;
    private boolean active;

    public FilterItem(){
        this.filterText = "";
        this.filterAt = EFilterAt.Any;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public EFilterAt getFilterAt() {
        return filterAt;
    }

    public void setFilterAt(EFilterAt filterAt) {
        this.filterAt = filterAt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
