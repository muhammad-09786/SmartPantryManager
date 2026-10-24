package com.example.smartpantrymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecipeMatcherTest {


    private Recipe omelette() {
        List<RecipeIngredient> ing = new ArrayList<>();
        ing.add(new RecipeIngredient("Eggs", 3, "pcs"));
        ing.add(new RecipeIngredient("Tomato", 1, "pcs"));
        ing.add(new RecipeIngredient("Oil", 1, "tbsp"));
        return new Recipe(1, "Tomato Omelette", "steps", ing);
    }

    @Test
    public void normalizeName_handlesPlurals() {
        assertEquals("tomato", IngredientMatcher.normalizeName("Tomatoes"));
        assertEquals("egg", IngredientMatcher.normalizeName("  EGGS "));
        assertEquals("berry", IngredientMatcher.normalizeName("Berries"));
        assertEquals("onion", IngredientMatcher.normalizeName("Onions"));
    }

    @Test
    public void allIngredientsPresent_recipeIsSuggested() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "eggs", 6, "pcs", ""),
                new PantryItem(2, "Tomatoes", 2, "pcs", ""),
                new PantryItem(3, "Oil", 1, "tbsp", ""));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(pantry);
        assertTrue(RecipeMatcher.canMake(omelette(), totals));
    }

    @Test
    public void oneMissingIngredient_recipeIsNotSuggested() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "Eggs", 6, "pcs", ""),
                new PantryItem(2, "Tomato", 2, "pcs", "")); // no oil
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(pantry);
        assertFalse(RecipeMatcher.canMake(omelette(), totals));
    }

    @Test
    public void notEnoughQuantity_recipeIsNotSuggested() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "Eggs", 2, "pcs", ""), // needs 3
                new PantryItem(2, "Tomato", 1, "pcs", ""),
                new PantryItem(3, "Oil", 1, "tbsp", ""));
        Map<String, Double> totals = RecipeMatcher.buildPantryTotals(pantry);
        assertFalse(RecipeMatcher.canMake(omelette(), totals));
    }

    @Test
    public void unitDifferences_areConverted() {
        List<RecipeIngredient> ing = new ArrayList<>();
        ing.add(new RecipeIngredient("Flour", 150, "g"));
        Recipe r = new Recipe(2, "Test", "steps", ing);

        List<PantryItem> pantry = Arrays.asList(new PantryItem(1, "Flour", 1, "kg", ""));
        assertTrue(RecipeMatcher.canMake(r, RecipeMatcher.buildPantryTotals(pantry)));
    }

    @Test
    public void duplicatePantryEntries_areSummed() {
        List<RecipeIngredient> ing = new ArrayList<>();
        ing.add(new RecipeIngredient("Eggs", 3, "pcs"));
        Recipe r = new Recipe(3, "Test", "steps", ing);

        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "Eggs", 2, "pcs", ""),
                new PantryItem(2, "eggs", 2, "pcs", ""));
        assertTrue(RecipeMatcher.canMake(r, RecipeMatcher.buildPantryTotals(pantry)));
    }

    @Test
    public void emptyPantry_noRecipesSuggested() {
        List<Recipe> all = Arrays.asList(omelette());
        List<PantryItem> empty = new ArrayList<>();
        assertEquals(0, RecipeMatcher.getSuggested(all, empty).size());
    }
}