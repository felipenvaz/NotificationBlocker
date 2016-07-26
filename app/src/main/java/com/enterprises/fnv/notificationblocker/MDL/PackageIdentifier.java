package com.enterprises.fnv.notificationblocker.MDL;

import android.content.Context;

import com.enterprises.fnv.notificationblocker.R;

/**
 * Created by FELIPE on 25/12/2015.
 */
public class PackageIdentifier {
    public String packageName;
    public String appName;

    @Override
    public String toString() {
        return appName;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()){
            return false;
        }
        return ((PackageIdentifier)o).packageName.equals(this.packageName);
    }

    public static PackageIdentifier getAnyOption(Context context){
        PackageIdentifier packageIdentifier = new PackageIdentifier();
        packageIdentifier.appName = context.getString(R.string.any);
        packageIdentifier.packageName = "";

        return packageIdentifier;
    }
}
