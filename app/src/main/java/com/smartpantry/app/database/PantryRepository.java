package com.smartpantry.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartpantry.app.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class that provides add/view/update/delete access to the
 * pantry_items table via PantryDbHelper.
 */
public class PantryRepository {

    private final PantryDbHelper dbHelper;

    public PantryRepository(Context context) {
        dbHelper = new PantryDbHelper(context);
    }

    /**
     * Inserts a new pantry item into the database.
     * @return the row id of the newly inserted item, or -1 if the insert failed.
     */
    public long addItem(PantryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COLUMN_PANTRY_NAME, item.getName());
        values.put(PantryDbHelper.COLUMN_PANTRY_QUANTITY, item.getQuantity());
        values.put(PantryDbHelper.COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(PantryDbHelper.COLUMN_PANTRY_EXPIRY, item.getExpiryDate());

        long newId = db.insert(PantryDbHelper.TABLE_PANTRY_ITEMS, null, values);
        db.close();
        return newId;
    }

    /**
     * Returns every pantry item currently stored in the database.
     */
    public List<PantryItem> getAllItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_PANTRY_ITEMS,
                null,
                null,
                null,
                null,
                null,
                PantryDbHelper.COLUMN_PANTRY_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                items.add(cursorToPantryItem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    /**
     * Returns a single pantry item by id, or null if no item has that id.
     */
    public PantryItem getItemById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                PantryDbHelper.TABLE_PANTRY_ITEMS,
                null,
                PantryDbHelper.COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        PantryItem item = null;
        if (cursor.moveToFirst()) {
            item = cursorToPantryItem(cursor);
        }

        cursor.close();
        db.close();
        return item;
    }

    /**
     * Updates an existing pantry item, matched by its id.
     * @return the number of rows affected (should be 1 on success, 0 if not found).
     */
    public int updateItem(PantryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COLUMN_PANTRY_NAME, item.getName());
        values.put(PantryDbHelper.COLUMN_PANTRY_QUANTITY, item.getQuantity());
        values.put(PantryDbHelper.COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(PantryDbHelper.COLUMN_PANTRY_EXPIRY, item.getExpiryDate());

        int rowsAffected = db.update(
                PantryDbHelper.TABLE_PANTRY_ITEMS,
                values,
                PantryDbHelper.COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();
        return rowsAffected;
    }

    /**
     * Deletes a pantry item by id.
     * @return the number of rows deleted (should be 1 on success, 0 if not found).
     */
    public int deleteItem(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                PantryDbHelper.TABLE_PANTRY_ITEMS,
                PantryDbHelper.COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsDeleted;
    }

    /**
     * Maps the current row of a Cursor to a PantryItem object.
     */
    private PantryItem cursorToPantryItem(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(PantryDbHelper.COLUMN_PANTRY_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COLUMN_PANTRY_NAME));
        double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(PantryDbHelper.COLUMN_PANTRY_QUANTITY));
        String unit = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COLUMN_PANTRY_UNIT));
        String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(PantryDbHelper.COLUMN_PANTRY_EXPIRY));

        return new PantryItem(id, name, quantity, unit, expiryDate);
    }
}