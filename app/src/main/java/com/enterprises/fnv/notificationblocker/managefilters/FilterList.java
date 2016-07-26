package com.enterprises.fnv.notificationblocker.managefilters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.enterprises.fnv.notificationblocker.DAO.FilterDAO;
import com.enterprises.fnv.notificationblocker.R;

/**
 * Created by FELIPE on 28/12/2015.
 */
public class FilterList extends AppCompatActivity implements View.OnClickListener{

    private FilterAdapter adapter;
    private FilterDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_list);

        Button add = (Button)findViewById(R.id.filter_list_add);
        add.setOnClickListener(this);

        dao = new FilterDAO(this);
        ListView filterList = (ListView)findViewById(R.id.filter_list_list);
        adapter = new FilterAdapter(this, dao.getAllFilters(FilterDAO.ANY_PACKAGE, false));
        filterList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        adapter.setFilterList(dao.getAllFilters(FilterDAO.ANY_PACKAGE, false));
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.filter_list_add:
                startActivity(new Intent(this, FilterManagerActivity.class));
                break;
        }
    }
}
