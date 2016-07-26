package com.enterprises.fnv.notificationblocker.MDL;

import java.util.List;

/**
 * Created by fnv on 22/12/2015.
 */
public class Filter {
    private String name;
    private PackageIdentifier packageFilter;
    private List<FilterItem> items;
    private long id;
    private boolean active;

    public Filter(){
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackageIdentifier getPackageFilter() {
        return packageFilter;
    }

    public void setPackageFilter(PackageIdentifier packageFilter) {
        this.packageFilter = packageFilter;
    }

    public List<FilterItem> getItems() {
        return items;
    }

    public void setItems(List<FilterItem> items) {
        this.items = items;
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
