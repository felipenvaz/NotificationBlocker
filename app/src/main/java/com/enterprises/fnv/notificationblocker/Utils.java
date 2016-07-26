package com.enterprises.fnv.notificationblocker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.enterprises.fnv.notificationblocker.DAO.FilterDAO;
import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.MDL.PackageIdentifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by FELIPE on 28/12/2015.
 */
public class Utils {

    /**
     *
     * @param context
     * @param managingFilter
     * @return All PackageIdentifiers that are not already used by a filter except the managingFilter
     */
    public static List<PackageIdentifier> getAllAvailableIdentifiers(Context context, Filter managingFilter){

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<PackageIdentifier> packageIdentifiers = new ArrayList<>();

        List<PackageIdentifier> userPackages = new FilterDAO(context).getAllUsedPackages();
        if(managingFilter != null) {
            userPackages.remove(managingFilter.getPackageFilter());
        }


        for(ApplicationInfo app : packages){

            PackageIdentifier packageIdentifier = new PackageIdentifier();
            packageIdentifier.appName = pm.getApplicationLabel(app).toString();
            packageIdentifier.packageName = app.packageName;
            packageIdentifiers.add(packageIdentifier);
        }

        Collections.sort(packageIdentifiers, new Comparator<PackageIdentifier>() {
            @Override
            public int compare(PackageIdentifier lhs, PackageIdentifier rhs) {
                return lhs.appName.compareTo(rhs.appName);
            }
        });
        packageIdentifiers.add(0, PackageIdentifier.getAnyOption(context));

        packageIdentifiers.removeAll(userPackages);



        return packageIdentifiers;
    }

    public static boolean stringContainsOther(String str1, String str2){
        return str1.toLowerCase().contains(str2.toLowerCase());
    }
}
