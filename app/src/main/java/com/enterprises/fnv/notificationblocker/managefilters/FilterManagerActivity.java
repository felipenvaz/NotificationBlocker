package com.enterprises.fnv.notificationblocker.managefilters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.enterprises.fnv.notificationblocker.DAO.FilterDAO;
import com.enterprises.fnv.notificationblocker.MDL.EFilterAt;
import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.MDL.FilterItem;
import com.enterprises.fnv.notificationblocker.MDL.PackageIdentifier;
import com.enterprises.fnv.notificationblocker.R;
import com.enterprises.fnv.notificationblocker.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FELIPE on 24/12/2015.
 */
public class FilterManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ADD_FILTER_ITEM_CODE = 1;
    public static final String MANAGE_FILTER_ID = "manage_filter_id";
    private Filter mFilter = null;
    FilterDAO filterDAO = new FilterDAO(this);

    private EditText filterName = null;
    private Spinner packageFilter = null;
    private Switch filterActive = null;

    FilterItemAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_manager);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            long id = bundle.getLong(MANAGE_FILTER_ID, -1);

            if (id != -1) {
                mFilter = filterDAO.getFilterById(id);
            }
        }
        List<PackageIdentifier> packageIdentifiers = Utils.getAllAvailableIdentifiers(this, mFilter);
        setAllViews();
        packageFilter.setAdapter(new ArrayAdapter<PackageIdentifier>(this, R.layout.filter_spinner_item, packageIdentifiers));

        ListView filterItems = (ListView)findViewById(R.id.filter_manager_items);
        if(mFilter == null) {
            itemsAdapter = new FilterItemAdapter(this, new ArrayList<FilterItem>());
        }else{
            itemsAdapter = new FilterItemAdapter(this, mFilter.getItems());
        }
        filterItems.setAdapter(itemsAdapter);

        ((Button)findViewById(R.id.filter_manager_add_item)).setOnClickListener(this);
        ((Button)findViewById(R.id.filter_manager_remove_item)).setOnClickListener(this);
        ((Button)findViewById(R.id.filter_manager_save)).setOnClickListener(this);
        Button removeFilter = ((Button)findViewById(R.id.filter_manager_remove));

        if(mFilter != null) {

            filterName.setText(mFilter.getName());
            packageFilter.setSelection(packageIdentifiers.indexOf(mFilter.getPackageFilter()));
            filterActive.setChecked(mFilter.isActive());

            removeFilter.setOnClickListener(this);
        }else{
            removeFilter.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ADD_FILTER_ITEM_CODE:
                FilterItem item = new FilterItem();
                if(data != null) {
                    Bundle b = data.getExtras();
                    if(b != null) {
                        item.setFilterText(b.getString(AddFilterItemDialogActivity.TEXT));
                        item.setFilterAt(EFilterAt.getFromId(b.getInt(AddFilterItemDialogActivity.AT)));
                        item.setActive(true);
                        itemsAdapter.addItem(item);
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.filter_manager_add_item:
                startActivityForResult(new Intent(this, AddFilterItemDialogActivity.class), ADD_FILTER_ITEM_CODE);
                break;
            case R.id.filter_manager_remove_item:
                itemsAdapter.removeItem();
                break;
            case R.id.filter_manager_save:
                setAllViews();
                Filter filter = new Filter();
                filter.setName(filterName.getText().toString());
                filter.setPackageFilter((PackageIdentifier) packageFilter.getSelectedItem());
                filter.setItems(itemsAdapter.getAllItems());
                filter.setActive(filterActive.isChecked());
                if(mFilter == null) {
                    filterDAO.create(filter);
                }else{
                    filter.setId(mFilter.getId());
                    filterDAO.updateFilter(filter);
                }
                finish();
                break;
            case R.id.filter_manager_remove:
                if(mFilter != null) {
                    filterDAO.delete(mFilter.getId());
                    finish();
                }
                break;
        }
    }

    private void setAllViews(){
        if(filterName == null){
            filterName = (EditText) findViewById(R.id.filter_manager_name);
        }
        if(packageFilter == null){
            packageFilter = (Spinner) findViewById(R.id.filter_manager_package);
        }
        if(filterActive == null){
            filterActive = (Switch)findViewById(R.id.filter_manager_switch);
        }
    }
}
