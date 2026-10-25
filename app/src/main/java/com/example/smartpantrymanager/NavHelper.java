package com.example.smartpantrymanager;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavHelper {


    public static void setup(Activity activity, int currentItemId) {
        BottomNavigationView nav = activity.findViewById(R.id.bottomNav);
        nav.setSelectedItemId(currentItemId);

        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == currentItemId) return true;

            Class<?> target;
            if (id == R.id.nav_pantry) {
                target = MainActivity.class;
            } else if (id == R.id.nav_recipes) {
                target = SuggestedRecipesActivity.class;
            } else {
                target = SettingsActivity.class;
            }

            Intent intent = new Intent(activity, target);
            if (target == MainActivity.class) {

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
            } else {
                activity.startActivity(intent);

                if (!(activity instanceof MainActivity)) {
                    activity.finish();
                }
            }
            activity.overridePendingTransition(0, 0);
            return true;
        });
    }
}