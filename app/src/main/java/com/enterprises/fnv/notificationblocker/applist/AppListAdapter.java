package com.enterprises.fnv.notificationblocker.applist;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.fnv.notificationblocker.R;

import java.util.List;

/**
 * Created by FELIPE on 13/12/2015.
 */
public class AppListAdapter extends BaseAdapter {

    private List<ApplicationInfo> apps;
    private Context mContext;

    public AppListAdapter(Context context, List<ApplicationInfo> apps){
        this.apps = apps;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.app_item, parent, false);
            holder = new AppViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.app_item_image);
            holder.name = (TextView)convertView.findViewById(R.id.app_item_text);
            convertView.setTag(holder);

            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo app = apps.get(position);

            holder.img.setImageDrawable(pm.getApplicationIcon(app));
            holder.name.setText(pm.getApplicationLabel(app));
        }
//        else{
//            holder = (AppViewHolder)convertView.getTag();
//        }

        return convertView;

    }

    class AppViewHolder{
        ImageView img;
        TextView name;
    }
}
