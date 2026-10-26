# Smart Pantry Manager

Mobile App Development 700, Richfield Graduate Institute of Technology
[Muhammad Cassim], Student ITS No: [402414434]

## What is this?

Smart Pantry Manager is an Android app I built in Java to help cut down on food waste. You tell it what's in your kitchen, and it tells you what you can cook with it. No shopping trip needed.

The idea that everything else depends on is what I call the strict-matching rule. A recipe only shows up if you have every single ingredient it needs, in at least the amount it needs. If a recipe needs five ingredients and you only have four, you won't see it. I wanted the app to never suggest something you can't actually make.

Real kitchens are messy, so the matching handles a few things for you:

- "Tomato" and "tomatoes" count as the same ingredient, and capital letters don't matter.
- Units are converted, so 1 kg of flour covers a recipe that asks for 150 g.
- If you enter the same ingredient twice (say, two separate entries of eggs), the amounts are added together.

## What it can do

You can add, edit and delete pantry items, each with a name, quantity, unit and an optional expiry date. The app comes with 17 recipes already loaded, and the Recipes tab shows only the ones you can make right now. If nothing matches, you get a friendly message instead of an empty screen. Tap a recipe to see its full ingredient list and method.

There's also a Settings screen where you can switch on expiry alerts (items that expire within three days turn red in your pantry list) and pick a preferred unit style. A bottom navigation bar moves you between Pantry, Recipes and Settings, and the Add/Edit form checks your input before saving.

## Why SQLite?

I went with SQLite, using `SQLiteOpenHelper`. It lives on the phone, so the app works without internet and there's no server to set up. Your pantry is still there after you close and reopen the app. The data fits it well too, because one recipe has many ingredients, which is a natural fit for linked tables.

There are three tables:

| Table | Columns |
|---|---|
| `pantry` | id, name, quantity, unit, expiry |
| `recipes` | id, name, steps |
| `recipe_ingredients` | id, recipe_id (links to recipes), ingredient_name, quantity, unit |

## How the code is organised

- `MainActivity` is the pantry list.
- `AddEditItemActivity` is the form for adding and editing, with validation.
- `SuggestedRecipesActivity` lists the recipes that pass the strict rule.
- `RecipeDetailActivity` shows one recipe in full.
- `SettingsActivity` holds the preferences, saved with SharedPreferences.
- `DatabaseHelper` creates the database, loads the recipes and handles all the CRUD.
- `IngredientMatcher` cleans up names and converts units.
- `RecipeMatcher` is where the strict rule lives.
- `PantryAdapter` and `RecipeAdapter` feed the two RecyclerViews.
- `NavHelper` sets up the bottom navigation so I didn't have to repeat it on every screen.

## How to run it

You'll need Android Studio and either an emulator or a phone running Android 7.0 (API 24) or newer.

1. Clone the repo: `git clone https://github.com/muhammad-09786/SmartPantryManager.git`
2. In Android Studio, choose File, then Open, and pick the `SmartPantryManager` folder.
3. Let Gradle finish syncing. This can take a few minutes the first time.
4. Pick an emulator in Device Manager, or plug in your phone with USB debugging on.
5. Press the green Run button.

To run the unit tests, right-click `RecipeMatcherTest` in the `test` folder and choose Run.

## Try it yourself

The quickest way to see the matching work: add Eggs (2, pcs), Bread (2, pcs) and Butter (10, g) to your pantry, then open the Recipes tab. Scrambled Eggs on Toast appears. Now delete the butter and check the tab again. The recipe is gone, because one missing ingredient is enough to rule it out.

One tip: enter your ingredients in the same kind of unit the recipes use (eggs in pcs, flour in g, milk in ml). The app can't convert between weight and volume.

## What's not perfect

- It can't convert grams to cups or the like, so those units won't match each other.
- The plural handling is a simple rule of thumb, not a full language tool.
- The units preference in Settings is saved, but it doesn't change the Add screen yet. That's the first thing I'd improve with more time.

## What I left out on purpose

The brief rules out maps and location features, so this app doesn't use Google Maps, any mapping SDK or GPS. It only deals with your own pantry and recipes.