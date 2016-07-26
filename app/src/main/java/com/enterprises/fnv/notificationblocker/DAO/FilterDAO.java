package com.enterprises.fnv.notificationblocker.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.enterprises.fnv.notificationblocker.MDL.Filter;
import com.enterprises.fnv.notificationblocker.MDL.PackageIdentifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FELIPE on 25/12/2015.
 */
public class FilterDAO {
    public static String TABLE_NAME = "filter";
    public static String TABLE_CREATE = "create table "  + TABLE_NAME + "( id integer primary key autoincrement, package TEXT NOT NULL, name TEXT NOT NULL, appName TEXT NOT NULL, active BIT NOT NULL)";

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public static final String ANY_PACKAGE = "%";

    public FilterDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.mContext = context;
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void create(final Filter filter) {

        // Steps to insert data into db (instead of using raw SQL query)
        // first, Create an object of ContentValues
        final ContentValues values = new ContentValues();

        // second, put the key-value pair into it
        values.put("package", filter.getPackageFilter().packageName);
        values.put("name", filter.getName().trim());
        values.put("appName", filter.getPackageFilter().appName);
        values.put("active", filter.isActive());

        try {
            open();
            // thirst. insert the object into the database
            filter.setId(database.insert(TABLE_NAME, null, values));
        }finally {
            close();
        }

        FilterItemDAO itemDAO = new FilterItemDAO(mContext);
        itemDAO.insertItems(filter);
    }

    public void delete(long filterId){
        try {
            open();
            // thirst. insert the object into the database
            database.delete(TABLE_NAME, "id = ?", new String[]{filterId + ""});
        }finally {
            close();
        }
    }

    public List<Filter> getAllFilters(String packageName, boolean onlyActive) {

        // Steps to fetch all records from a database table
        // first, create the desired object
        final List<Filter> filters = new ArrayList<>();

        try {
            open();
            // second, Query the database and set the result into Cursor
            final Cursor cursor = database.query(TABLE_NAME, new String[]{"id", "package", "name", "appName", "active"}, "(package like ? or package like '')"
                    + (onlyActive ? " AND active = 1" : ""), new String[]{ packageName }, null, null, null);

            // Move the Cursor pointer to the first
            cursor.moveToFirst();

            //Iterate over the cursor
            while (!cursor.isAfterLast()) {
                Filter filter = new Filter();

                // Fetch the desired value from the Cursor by column index
                filter.setId(cursor.getLong(0));

                PackageIdentifier p = new PackageIdentifier();
                p.packageName = cursor.getString(1);
                p.appName = cursor.getString(3);
                filter.setPackageFilter(p);

                filter.setName(cursor.getString(2));
                filter.setActive(cursor.getInt(4) > 0);

                // Add the object filled with appropriate data into the list
                filters.add(filter);

                // Move the Cursor pointer to next for the next record to fetch
                cursor.moveToNext();
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        FilterItemDAO itemDAO = new FilterItemDAO(mContext);
        for(Filter filter : filters){
            filter = itemDAO.getAllFilterItems(filter, onlyActive);
        }

        return filters;
    }

    public List<PackageIdentifier> getAllUsedPackages(){
        final ArrayList<PackageIdentifier> packages = new ArrayList<>();

        try {
            open();
            // second, Query the database and set the result into Cursor
            final Cursor cursor = database.query(TABLE_NAME, new String[]{"package"}, null, null, null, null, null);

            // Move the Cursor pointer to the first
            cursor.moveToFirst();

            //Iterate over the cursor
            while (!cursor.isAfterLast()) {
                PackageIdentifier packageIdentifier = new PackageIdentifier();

                // Fetch the desired value from the Cursor by column index
                packageIdentifier.packageName = cursor.getString(0);

                // Add the object filled with appropriate data into the list
                packages.add(packageIdentifier);

                // Move the Cursor pointer to next for the next record to fetch
                cursor.moveToNext();
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        return packages;
    }

    public Filter getFilterById(long id){
        Filter filter = null;

        try {
            open();
            // second, Query the database and set the result into Cursor
            final Cursor cursor = database.query(TABLE_NAME, new String[]{"id", "package", "name", "appName", "active"}, "id = "+id, null, null, null, null);

            // Move the Cursor pointer to the first
            cursor.moveToFirst();

            //Iterate over the cursor
            if (!cursor.isAfterLast()) {
                // Fetch the desired value from the Cursor by column index
                filter = new Filter();
                filter.setId(cursor.getLong(0));
                PackageIdentifier p = new PackageIdentifier();
                p.packageName = cursor.getString(1);
                p.appName = cursor.getString(3);
                filter.setActive(cursor.getInt(4) > 0);

                filter.setPackageFilter(p);
                filter.setName(cursor.getString(2));
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        if(filter != null && filter.getId() != -1) {
            FilterItemDAO itemDAO = new FilterItemDAO(mContext);
            filter = itemDAO.getAllFilterItems(filter, false);
        }

        return filter;
    }

    public void updateFilter(Filter filter){
        // Steps to insert data into db (instead of using raw SQL query)
        // first, Create an object of ContentValues
        final ContentValues values = new ContentValues();

        // second, put the key-value pair into it
        values.put("package", filter.getPackageFilter().packageName);
        values.put("name", filter.getName());
        values.put("appName", filter.getPackageFilter().appName);
        values.put("active", filter.isActive());

        try {
            open();
            // thirst. insert the object into the database
            database.update(TABLE_NAME, values, "id = " + filter.getId(), null);
        }finally {
            close();
        }

        FilterItemDAO itemDAO = new FilterItemDAO(mContext);
        itemDAO.deleteItems(filter.getId());
        itemDAO.insertItems(filter);
    }

    public void updateFilterActive(Filter filter){
        final ContentValues values = new ContentValues();
        values.put("active", filter.isActive());

        try {
            open();
            // thirst. insert the object into the database
            database.update(TABLE_NAME, values, "id = "+filter.getId(), null);
        }finally {
            close();
        }
    }
}
