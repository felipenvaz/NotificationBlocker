package com.enterprises.fnv.notificationblocker.notificationlist;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.fnv.notificationblocker.NotificationApp;
import com.enterprises.fnv.notificationblocker.R;

import java.util.List;

/**
 * Created by FELIPE on 19/12/2015.
 */
public class NotificationListAdapter extends BaseAdapter {

    private Context mContext;
    private List<NotificationApp> notificationList;

    public NotificationListAdapter(Context context, List<NotificationApp> notifications){
        this.mContext = context;
        this.notificationList = notifications;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationViewHolder holder;
        NotificationApp notificationApp = notificationList.get(position);

        if(convertView==null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.notification_app, parent, false);
            holder = new NotificationViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.notification_app_image);
            holder.title = (TextView)convertView.findViewById(R.id.notification_app_title);
            holder.text = (TextView)convertView.findViewById(R.id.notification_app_text);
            holder.subtext = (TextView)convertView.findViewById(R.id.notification_app_subtext);
            holder.appname = (TextView)convertView.findViewById(R.id.notification_app_appname);
            convertView.setTag(holder);
            setContent(holder, notificationApp);
        }
        else{
            holder = (NotificationViewHolder)convertView.getTag();
            if(holder.id != notificationApp.getId()){
                setContent(holder, notificationApp);
            }
        }

        return convertView;
    }

    private void setContent(NotificationViewHolder holder, NotificationApp notificationApp){
        holder.id = notificationApp.getId();
        PackageManager pm = mContext.getPackageManager();
        try {
            holder.img.setImageDrawable(pm.getApplicationIcon(notificationApp.getPackage()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.title.setText(notificationApp.getTitle());
        holder.text.setText(notificationApp.getMainText());
        holder.subtext.setText(notificationApp.getSubText());
        try {
            holder.appname.setText(pm.getApplicationLabel(pm.getApplicationInfo(notificationApp.getPackage(), 0)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    class NotificationViewHolder{
        ImageView img;
        TextView title;
        TextView text;
        TextView subtext;
        TextView appname;
        long id;
    }
}
