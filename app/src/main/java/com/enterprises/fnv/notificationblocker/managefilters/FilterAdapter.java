package com.enterprises.fnv.notificationblocker.managefilters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.enterprises.fnv.notificationblocker.DAO.FilterDAO;
import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.R;

import java.util.List;

/**
 * Created by FELIPE on 28/12/2015.
 */
public class FilterAdapter extends BaseAdapter {
    List<Filter> mFiltersList;
    Context mContext;

    public FilterAdapter(Context context, List<Filter> filters){
        this.mContext = context;
        this.mFiltersList = filters;
    }

    public void setFilterList(List<Filter> filters){
        mFiltersList = filters;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFiltersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFiltersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFiltersList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Filter filter = mFiltersList.get(position);

        if(convertView == null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.filter_adapter, parent, false);

            FilterHolder holder = new FilterHolder();
            holder.name = (TextView)convertView.findViewById(R.id.filter_adapter_name);
            holder.app = (TextView)convertView.findViewById(R.id.filter_adapter_app);
            holder.active = (Switch)convertView.findViewById(R.id.filter_adapter_switch);
            holder.edit = (Button)convertView.findViewById(R.id.filter_adapter_edit);
            holder.remove = (Button)convertView.findViewById(R.id.filter_adapter_remove);

            setContent(holder, filter);
            convertView.setTag(holder);
        }else{
            FilterHolder holder = (FilterHolder)convertView.getTag();
            if(holder.id != filter.getId()){
                setContent(holder, filter);
            }
        }

        return convertView;
    }

    private void setContent(FilterHolder holder, final Filter filter){
        holder.id = filter.getId();
        holder.name.setText(mContext.getString(R.string.filter_name) + " " + filter.getName());
        holder.app.setText(mContext.getString(R.string.filter_app_name) + " " + filter.getPackageFilter().appName);
        holder.active.setChecked(filter.isActive());
        holder.active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filter.setActive(isChecked);
                new FilterDAO(mContext).updateFilterActive(filter);
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.filter_adapter_edit:
                        Intent i = new Intent(mContext, FilterManagerActivity.class);
                        i.putExtra(FilterManagerActivity.MANAGE_FILTER_ID, filter.getId());
                        mContext.startActivity(i);
                        break;
                }
            }
        };

        holder.edit.setOnClickListener(clickListener);
        holder.remove.setOnClickListener(clickListener);
    }

    private class FilterHolder{
        long id;
        TextView name;
        TextView app;
        Switch active;
        Button edit;
        Button remove;
    }
}
