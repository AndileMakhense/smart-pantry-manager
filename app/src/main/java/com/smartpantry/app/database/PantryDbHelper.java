package com.smartpantry.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PantryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table: pantry_items
    public static final String TABLE_PANTRY_ITEMS = "pantry_items";
    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "name";
    public static final String COLUMN_PANTRY_QUANTITY = "quantity";
    public static final String COLUMN_PANTRY_UNIT = "unit";
    public static final String COLUMN_PANTRY_EXPIRY = "expiry_date";

    // Table: recipes
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_STEPS = "steps";

    // Table: recipe_ingredients
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COLUMN_RI_ID = "id";
    public static final String COLUMN_RI_RECIPE_ID = "recipe_id"; // foreign key to recipes.id
    public static final String COLUMN_RI_NAME = "name";
    public static final String COLUMN_RI_QUANTITY = "quantity";
    public static final String COLUMN_RI_UNIT = "unit";

    public PantryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create pantry_items table
        String createPantryItems = "CREATE TABLE " + TABLE_PANTRY_ITEMS + " (" +
                COLUMN_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PANTRY_NAME + " TEXT NOT NULL, " +
                COLUMN_PANTRY_QUANTITY + " REAL NOT NULL, " +
                COLUMN_PANTRY_UNIT + " TEXT NOT NULL, " +
                COLUMN_PANTRY_EXPIRY + " TEXT" +
                ")";
        db.execSQL(createPantryItems);

        // Create recipes table
        String createRecipes = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                COLUMN_RECIPE_STEPS + " TEXT NOT NULL" +
                ")";
        db.execSQL(createRecipes);

        // Create recipe_ingredients table, linked to recipes via recipe_id
        String createRecipeIngredients = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COLUMN_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RI_RECIPE_ID + " INTEGER NOT NULL, " +
                COLUMN_RI_NAME + " TEXT NOT NULL, " +
                COLUMN_RI_QUANTITY + " REAL NOT NULL, " +
                COLUMN_RI_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_RI_RECIPE_ID + ") REFERENCES " +
                TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + ")" +
                ")";
        db.execSQL(createRecipeIngredients);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple upgrade strategy: drop and recreate everything
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEMS);
        onCreate(db);
    }
}