package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "pantry_prefs";
    public static final String KEY_EXPIRY_ALERTS = "expiry_alerts";
    public static final String KEY_UNITS = "units";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Switch switchExpiry = findViewById(R.id.switchExpiry);
        RadioGroup radioUnits = findViewById(R.id.radioUnits);
        RadioButton radioMetric = findViewById(R.id.radioMetric);
        RadioButton radioCooking = findViewById(R.id.radioCooking);


        switchExpiry.setChecked(prefs.getBoolean(KEY_EXPIRY_ALERTS, true));
        String units = prefs.getString(KEY_UNITS, "metric");
        if (units.equals("cooking")) {
            radioCooking.setChecked(true);
        } else {
            radioMetric.setChecked(true);
        }


        switchExpiry.setOnCheckedChangeListener((button, isChecked) ->
                prefs.edit().putBoolean(KEY_EXPIRY_ALERTS, isChecked).apply());

        radioUnits.setOnCheckedChangeListener((group, checkedId) -> {
            String value = (checkedId == R.id.radioCooking) ? "cooking" : "metric";
            prefs.edit().putString(KEY_UNITS, value).apply();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavHelper.setup(this, R.id.nav_settings);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}