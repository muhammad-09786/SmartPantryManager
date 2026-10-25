package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
        DatabaseHelper db = new DatabaseHelper(this);
        Recipe recipe = db.getRecipe(recipeId);

        if (recipe == null) {
            finish();
            return;
        }

        setTitle(recipe.getName());

        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvSteps = findViewById(R.id.tvDetailSteps);

        tvName.setText(recipe.getName());
        tvIngredients.setText(buildIngredientText(recipe));
        tvSteps.setText(recipe.getSteps());
    }


    private String buildIngredientText(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ri : recipe.getIngredients()) {
            double q = ri.getQuantity();
            String qty = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
            sb.append("- ").append(qty).append(" ").append(ri.getUnit())
                    .append(" ").append(ri.getName()).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}