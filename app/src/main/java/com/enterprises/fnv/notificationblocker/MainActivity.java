package com.enterprises.fnv.notificationblocker;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.enterprises.fnv.notificationblocker.DAO.NotificationDAO;
import com.enterprises.fnv.notificationblocker.managefilters.FilterList;
import com.enterprises.fnv.notificationblocker.notificationlist.NotificationListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.main_filters)).setOnClickListener(this);
        ((Button)findViewById(R.id.main_settings)).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        NotificationDAO dao = new NotificationDAO(this);
        List<NotificationApp> notifications = dao.getAllNotifications();

        ListView listView = (ListView)findViewById(R.id.main_notifications_list);
        listView.setAdapter(new NotificationListAdapter(this, notifications));

        if(!NotificationInterceptor.isNotificationAccessEnabled){
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_filters:
                startActivity(new Intent(this, FilterList.class));
                break;
            case R.id.main_settings:
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                break;
        }
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
