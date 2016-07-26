package com.enterprises.fnv.notificationblocker.applist;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import com.enterprises.fnv.notificationblocker.R;

import java.util.List;

/**
 * Created by FELIPE on 14/12/2015.
 */
public class AppList extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_list);
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        AppListAdapter adapter = new AppListAdapter(this, packages);
        ((ListView)findViewById(R.id.app_list)).setAdapter(adapter);
    }
}
