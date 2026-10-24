package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "pantry.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry TEXT)");
        createRecipeTables(db);
        seedRecipes(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        createRecipeTables(db);
        seedRecipes(db);
    }

    private void createRecipeTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "steps TEXT NOT NULL)");

        db.execSQL("CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "ingredient_name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");
    }


    private void seedRecipes(SQLiteDatabase db) {
        insertRecipe(db, "Tomato Omelette",
                "1. Beat the eggs with the salt.\n2. Chop the tomato.\n3. Heat the oil in a pan.\n4. Add the tomato and cook for 1 minute.\n5. Pour in the eggs and cook until set.",
                "Eggs|3|pcs", "Tomato|1|pcs", "Oil|1|tbsp", "Salt|1|tsp");

        insertRecipe(db, "Scrambled Eggs on Toast",
                "1. Toast the bread.\n2. Melt the butter in a pan.\n3. Add the beaten eggs and stir gently until creamy.\n4. Serve on the toast.",
                "Eggs|2|pcs", "Bread|2|pcs", "Butter|10|g");

        insertRecipe(db, "Pancakes",
                "1. Mix the flour, egg, milk and sugar into a smooth batter.\n2. Heat a lightly oiled pan.\n3. Pour in a ladle of batter.\n4. Flip when bubbles appear and cook the other side.",
                "Flour|150|g", "Eggs|1|pcs", "Milk|200|ml", "Sugar|1|tbsp");

        insertRecipe(db, "Plain Boiled Rice",
                "1. Rinse the rice.\n2. Bring the water and salt to the boil.\n3. Add the rice, cover and simmer for 15 minutes.\n4. Rest for 5 minutes and fluff with a fork.",
                "Rice|1|cup", "Water|2|cup", "Salt|1|tsp");

        insertRecipe(db, "Egg Fried Rice",
                "1. Heat the oil in a pan.\n2. Scramble the eggs and set aside.\n3. Fry the cooked rice for 3 minutes.\n4. Add the soy sauce and the eggs, then stir well.",
                "Rice|2|cup", "Eggs|2|pcs", "Oil|1|tbsp", "Soy Sauce|1|tbsp");

        insertRecipe(db, "Cheese Toastie",
                "1. Butter the outside of the bread.\n2. Place the cheese between the slices.\n3. Toast in a pan for 2 minutes per side until golden.",
                "Bread|2|pcs", "Cheese|50|g", "Butter|10|g");

        insertRecipe(db, "Spaghetti with Tomato Sauce",
                "1. Boil the spaghetti until tender.\n2. Fry the chopped onion and garlic in the oil.\n3. Add the chopped tomatoes and simmer for 15 minutes.\n4. Toss the spaghetti in the sauce.",
                "Spaghetti|200|g", "Tomatoes|3|pcs", "Onion|1|pcs", "Garlic|2|pcs", "Oil|2|tbsp");

        insertRecipe(db, "Garlic Butter Potatoes",
                "1. Cut the potatoes into chunks and boil for 10 minutes.\n2. Drain well.\n3. Fry in the butter with the crushed garlic until golden.\n4. Season with salt.",
                "Potatoes|4|pcs", "Butter|30|g", "Garlic|2|pcs", "Salt|1|tsp");

        insertRecipe(db, "Mashed Potatoes",
                "1. Peel and boil the potatoes until soft.\n2. Drain and mash.\n3. Stir in the butter, warm milk and salt until smooth.",
                "Potatoes|4|pcs", "Butter|30|g", "Milk|100|ml", "Salt|1|tsp");

        insertRecipe(db, "Banana Pancakes",
                "1. Mash the bananas.\n2. Mix in the eggs and flour.\n3. Fry small spoonfuls in a pan until golden on both sides.",
                "Bananas|2|pcs", "Eggs|2|pcs", "Flour|50|g");

        insertRecipe(db, "Creamy Porridge",
                "1. Place the oats and milk in a pot.\n2. Cook over low heat for 5 minutes, stirring.\n3. Sweeten with the sugar and serve warm.",
                "Oats|50|g", "Milk|250|ml", "Sugar|1|tsp");

        insertRecipe(db, "Chicken Rice Bowl",
                "1. Dice the chicken and onion.\n2. Fry in the oil until the chicken is cooked through.\n3. Serve over the cooked rice.",
                "Chicken|300|g", "Rice|1|cup", "Onion|1|pcs", "Oil|1|tbsp");

        insertRecipe(db, "Simple Onion Soup",
                "1. Slice the onions thinly.\n2. Cook slowly in the butter for 15 minutes until soft.\n3. Stir in the flour, then add the water.\n4. Simmer for 15 minutes and season.",
                "Onions|3|pcs", "Butter|20|g", "Flour|1|tbsp", "Water|500|ml");

        insertRecipe(db, "Tuna Pasta",
                "1. Boil the pasta until tender.\n2. Fry the chopped onion in the oil.\n3. Add the tuna and warm through.\n4. Mix in the pasta.",
                "Pasta|200|g", "Tuna|1|pcs", "Onion|1|pcs", "Oil|1|tbsp");

        insertRecipe(db, "Vegetable Stir-Fry",
                "1. Slice the carrots, onion and cabbage.\n2. Heat the oil in a wok.\n3. Stir-fry for 5 minutes.\n4. Add the soy sauce and toss.",
                "Carrots|2|pcs", "Onion|1|pcs", "Cabbage|200|g", "Oil|1|tbsp", "Soy Sauce|1|tbsp");

        insertRecipe(db, "French Toast",
                "1. Whisk the eggs, milk and sugar.\n2. Soak the bread slices in the mixture.\n3. Fry in a pan until golden on both sides.",
                "Bread|4|pcs", "Eggs|2|pcs", "Milk|100|ml", "Sugar|1|tsp");

        insertRecipe(db, "Cheese Omelette",
                "1. Beat the eggs.\n2. Melt the butter in a pan and add the eggs.\n3. Sprinkle the grated cheese on top.\n4. Fold in half when set.",
                "Eggs|3|pcs", "Cheese|30|g", "Butter|10|g");
    }


    private void insertRecipe(SQLiteDatabase db, String name, String steps, String... ingredients) {
        ContentValues r = new ContentValues();
        r.put("name", name);
        r.put("steps", steps);
        long recipeId = db.insert("recipes", null, r);

        for (String line : ingredients) {
            String[] p = line.split("\\|"); // "Eggs|3|pcs" -> ["Eggs","3","pcs"]
            ContentValues v = new ContentValues();
            v.put("recipe_id", recipeId);
            v.put("ingredient_name", p[0]);
            v.put("quantity", Double.parseDouble(p[1]));
            v.put("unit", p[2]);
            db.insert("recipe_ingredients", null, v);
        }
    }


    public long addPantryItem(String name, double quantity, String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("pantry", null, toValues(name, quantity, unit, expiry));
    }


    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query("pantry", null, null, null, null, null,
                "name COLLATE NOCASE ASC")) {
            while (c.moveToNext()) {
                list.add(fromCursor(c));
            }
        }
        return list;
    }

    public PantryItem getPantryItem(int id) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query("pantry", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null)) {
            if (c.moveToFirst()) return fromCursor(c);
        }
        return null;
    }


    public int updatePantryItem(int id, String name, double quantity, String unit, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update("pantry", toValues(name, quantity, unit, expiry),
                "id = ?", new String[]{String.valueOf(id)});
    }


    public int deletePantryItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("pantry", "id = ?", new String[]{String.valueOf(id)});
    }


    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query("recipes", null, null, null, null, null,
                "name COLLATE NOCASE ASC")) {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndexOrThrow("id"));
                list.add(new Recipe(id,
                        c.getString(c.getColumnIndexOrThrow("name")),
                        c.getString(c.getColumnIndexOrThrow("steps")),
                        getIngredientsFor(db, id)));
            }
        }
        return list;
    }

    public Recipe getRecipe(int id) {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor c = db.query("recipes", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null)) {
            if (c.moveToFirst()) {
                return new Recipe(id,
                        c.getString(c.getColumnIndexOrThrow("name")),
                        c.getString(c.getColumnIndexOrThrow("steps")),
                        getIngredientsFor(db, id));
            }
        }
        return null;
    }

    private List<RecipeIngredient> getIngredientsFor(SQLiteDatabase db, int recipeId) {
        List<RecipeIngredient> list = new ArrayList<>();
        try (Cursor c = db.query("recipe_ingredients", null, "recipe_id = ?",
                new String[]{String.valueOf(recipeId)}, null, null, null)) {
            while (c.moveToNext()) {
                list.add(new RecipeIngredient(
                        c.getString(c.getColumnIndexOrThrow("ingredient_name")),
                        c.getDouble(c.getColumnIndexOrThrow("quantity")),
                        c.getString(c.getColumnIndexOrThrow("unit"))));
            }
        }
        return list;
    }


    private ContentValues toValues(String name, double quantity, String unit, String expiry) {
        ContentValues v = new ContentValues();
        v.put("name", name);
        v.put("quantity", quantity);
        v.put("unit", unit);
        v.put("expiry", expiry);
        return v;
    }

    private PantryItem fromCursor(Cursor c) {
        return new PantryItem(
                c.getInt(c.getColumnIndexOrThrow("id")),
                c.getString(c.getColumnIndexOrThrow("name")),
                c.getDouble(c.getColumnIndexOrThrow("quantity")),
                c.getString(c.getColumnIndexOrThrow("unit")),
                c.getString(c.getColumnIndexOrThrow("expiry")));
    }
}