package com.enterprises.fnv.notificationblocker.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.enterprises.fnv.notificationblocker.NotificationApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FELIPE on 27/09/2015.
 */
public class NotificationDAO {
    public static String TABLE_NAME = "notification";
    public static String TABLE_CREATE = "create table "  + TABLE_NAME + "( id integer primary key autoincrement, package TEXT NOT NULL, mainText TEXT NOT NULL, "
            +"title TEXT NOT NULL, subText TEXT, removed BIT NOT NULL);";
    public static String TRIGGER = "CREATE TRIGGER notification_trigger AFTER INSERT ON "+TABLE_NAME
            +" BEGIN "
            +" DELETE FROM "+TABLE_NAME+" WHERE id = (SELECT CASE WHEN COUNT(*) > 10 THEN MIN (ID) ELSE 0 END FROM "+TABLE_NAME+" WHERE removed = 0);"
            +" DELETE FROM "+TABLE_NAME+" WHERE id = (SELECT CASE WHEN COUNT(*) > 10 THEN MIN (ID) ELSE 0 END FROM "+TABLE_NAME+" WHERE removed = 1);"
            +" END;";

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public NotificationDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close(){
        dbHelper.close();
    }

    public void create(final NotificationApp notification) {

        // Steps to insert data into db (instead of using raw SQL query)
        // first, Create an object of ContentValues
        final ContentValues values = new ContentValues();

        // second, put the key-value pair into it
        values.put("package", notification.getPackage());
        values.put("mainText", notification.getMainText());
        values.put("title", notification.getTitle());
        values.put("subText", notification.getSubText());
        values.put("removed", notification.isRemoved());

        try {
            open();
            // thirst. insert the object into the database
            final long id = database.insert(TABLE_NAME, null, values);

            // set the primary key to object and return back
            notification.setId(id);
        }finally {
            close();
        }
    }

    public void delete(final NotificationApp notification) {
        try {
            open();
            // Way to delete a record from database
            database.delete(TABLE_NAME, "id = " + notification.getId(), null);
        }finally {
            close();
        }
    }

    public List<NotificationApp> getAllNotifications() {

        // Steps to fetch all records from a database table
        // first, create the desired object
        final List<NotificationApp> notifications = new ArrayList<NotificationApp>();

        try {
            open();
            // second, Query the database and set the result into Cursor
            final Cursor cursor = database.query(TABLE_NAME, new String[]{"id", "package", "mainText", "title", "subText"}, null, null, null, null, null);

            // Move the Cursor pointer to the first
            cursor.moveToFirst();

            //Iterate over the cursor
            while (!cursor.isAfterLast()) {
                final NotificationApp number = new NotificationApp();

                // Fetch the desired value from the Cursor by column index
                number.setId(cursor.getLong(0));
                number.setPackage(cursor.getString(1));
                number.setMainText(cursor.getString(2));
                number.setTitle(cursor.getString(3));
                number.setSubText(cursor.getString(4));

                // Add the object filled with appropriate data into the list
                notifications.add(number);

                // Move the Cursor pointer to next for the next record to fetch
                cursor.moveToNext();
            }
            cursor.close();
        }finally {
            close();
        }
        return notifications;
    }
}
