package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {


    public static Map<String, Double> buildPantryTotals(List<PantryItem> pantry) {
        Map<String, Double> totals = new HashMap<>();
        for (PantryItem item : pantry) {
            String key = IngredientMatcher.key(item.getName(), item.getUnit());
            double qty = IngredientMatcher.toBaseQty(item.getQuantity(), item.getUnit());
            Double current = totals.get(key);
            totals.put(key, (current == null ? 0 : current) + qty);
        }
        return totals;
    }


    public static boolean canMake(Recipe recipe, Map<String, Double> pantryTotals) {
        if (recipe.getIngredients().isEmpty()) return false;

        for (RecipeIngredient ri : recipe.getIngredients()) {
            String key = IngredientMatcher.key(ri.getName(), ri.getUnit());
            Double have = pantryTotals.get(key);
            double need = IngredientMatcher.toBaseQty(ri.getQuantity(), ri.getUnit());

            if (have == null || have + 0.000001 < need) {
                return false;
            }
        }
        return true;
    }


    public static List<Recipe> getSuggested(List<Recipe> allRecipes, List<PantryItem> pantry) {
        Map<String, Double> totals = buildPantryTotals(pantry);
        List<Recipe> result = new ArrayList<>();
        for (Recipe r : allRecipes) {
            if (canMake(r, totals)) {
                result.add(r);
            }
        }
        return result;
    }
}