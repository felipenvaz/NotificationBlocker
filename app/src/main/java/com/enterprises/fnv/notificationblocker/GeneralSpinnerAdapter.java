package com.enterprises.fnv.notificationblocker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fnv on 22/12/2015.
 */
public class GeneralSpinnerAdapter extends BaseAdapter {

    private List<SpinnerObject> objectsList;
    private Context mContext;

    public GeneralSpinnerAdapter(Context context, List<SpinnerObject> objects){
        this.mContext = context;
        this.objectsList = objects;
        if(this.objectsList == null) {
            this.objectsList = new ArrayList<SpinnerObject>();
        }
    }

    @Override
    public int getCount() {
        return objectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return objectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objectsList.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
