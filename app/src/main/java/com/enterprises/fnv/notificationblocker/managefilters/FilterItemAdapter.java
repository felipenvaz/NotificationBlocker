package com.enterprises.fnv.notificationblocker.managefilters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.enterprises.fnv.notificationblocker.MDL.FilterItem;
import com.enterprises.fnv.notificationblocker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fnv on 22/12/2015.
 */
public class FilterItemAdapter extends BaseAdapter{

    private List<FilterItem> filterList;
    private Context mContext;

    public FilterItemAdapter(Context context, List<FilterItem> filters){
        this.mContext = context;
        this.filterList = filters;
        if(this.filterList == null){
            this.filterList = new ArrayList<>();
        }
    }

    public void addItem(FilterItem item){
        filterList.add(item);
        this.notifyDataSetChanged();
    }

    public void removeItem(){
        if(filterList.size() > 0){
            filterList.remove(filterList.size()-1);
            this.notifyDataSetChanged();
        }
    }

    public List<FilterItem> getAllItems(){
        return filterList;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterList.get(position).getPosition();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.filter_item_show, parent, false);

            final FilterItem item = filterList.get(position);
            ((TextView) convertView.findViewById(R.id.filter_item_show_filter)).setText(item.getFilterText());
            ((TextView) convertView.findViewById(R.id.filter_item_show_at)).setText(item.getFilterAt().toString());
            Switch filterItemSwitch = (Switch) convertView.findViewById(R.id.filter_item_show_switch);
            filterItemSwitch.setChecked(item.isActive());
            filterItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setActive(isChecked);
                }
            });
        }

        return convertView;
    }
}
