package com.enterprises.fnv.notificationblocker.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.enterprises.fnv.notificationblocker.MDL.EFilterAt;
import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.MDL.FilterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FELIPE on 25/12/2015.
 */
public class FilterItemDAO {
    public static String TABLE_NAME = "filter_item";
    public static String TABLE_CREATE = "create table "  + TABLE_NAME + "( id INTEGER primary key autoincrement, filter_id INTEGER NOT NULL, textFilter TEXT NOT NULL, at INTEGER NOT NULL, active BIT NOT NULL)";

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public FilterItemDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insertItems(final Filter filter) {

        for (FilterItem item : filter.getItems()) {
            // Steps to insert data into db (instead of using raw SQL query)
            // first, Create an object of ContentValues
            final ContentValues values = new ContentValues();

            // second, put the key-value pair into it
            values.put("filter_id", filter.getId());
            values.put("textFilter", item.getFilterText().trim());
            values.put("at", item.getFilterAt().getItemId());
            values.put("active", item.isActive());

            try {
                open();
                // thirst. insert the object into the database
                item.setId(database.insert(TABLE_NAME, null, values));
            } finally {
                close();
            }
        }
    }

    public void deleteItems(long filterId){
        try {
            open();
            // thirst. insert the object into the database
            database.delete(TABLE_NAME, "filter_id = "+ filterId, null);
        }finally {
            close();
        }
    }

    public Filter getAllFilterItems(Filter filter, boolean onlyActive) {

        if(filter != null && filter.getId() != -1) {
            // Steps to fetch all records from a database table
            // first, create the desired object
            final List<FilterItem> items = new ArrayList<>();

            try {
                open();
                // second, Query the database and set the result into Cursor
                final Cursor cursor = database.query(TABLE_NAME, new String[]{"id", "textFilter", "at", "active"}, "filter_id = " + filter.getId()
                        + (onlyActive ? " AND active = 1" : ""), null, null, null, null);

                // Move the Cursor pointer to the first
                cursor.moveToFirst();

                //Iterate over the cursor
                while (!cursor.isAfterLast()) {
                    FilterItem item = new FilterItem();

                    // Fetch the desired value from the Cursor by column index
                    item.setId(cursor.getLong(0));
                    item.setFilterText(cursor.getString(1));
                    item.setFilterAt(EFilterAt.getFromId(cursor.getInt(2)));
                    item.setActive(cursor.getInt(3) > 0);

                    // Add the object filled with appropriate data into the list
                    items.add(item);

                    // Move the Cursor pointer to next for the next record to fetch
                    cursor.moveToNext();
                }
                cursor.close();
            } finally {
                close();
            }

            filter.setItems(items);
        }
        return filter;
    }

    public void updateFilterItemActive(FilterItem filterItem){
        final ContentValues values = new ContentValues();
        values.put("active", filterItem.isActive());

        try {
            open();
            // thirst. insert the object into the database
            database.update(TABLE_NAME, values, "id = "+filterItem.getId(), null);
        }finally {
            close();
        }
    }
}
