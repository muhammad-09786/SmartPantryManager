package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEditItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "item_id";
    private static final String[] UNITS = {"pcs", "g", "kg", "ml", "l", "tbsp", "tsp", "cup"};

    private DatabaseHelper db;
    private EditText etName, etQuantity, etExpiry;
    private Spinner spinnerUnit;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etExpiry = findViewById(R.id.etExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        Button btnSave = findViewById(R.id.btnSave);

        spinnerUnit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, UNITS));


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            setTitle("Edit Ingredient");
            loadItem();
        } else {
            setTitle("Add Ingredient");
        }

        etExpiry.setOnClickListener(v -> showDatePicker());
        etExpiry.setOnLongClickListener(v -> {
            etExpiry.setText("");
            return true;
        });
        btnSave.setOnClickListener(v -> saveItem());
    }


    private void loadItem() {
        PantryItem item = db.getPantryItem(itemId);
        if (item == null) { finish(); return; }
        etName.setText(item.getName());
        etQuantity.setText(String.valueOf(item.getQuantity()));
        List<String> units = Arrays.asList(UNITS);
        int pos = units.indexOf(item.getUnit());
        spinnerUnit.setSelection(Math.max(pos, 0));
        etExpiry.setText(item.getExpiry());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                etExpiry.setText(String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day)),
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String qtyText = etQuantity.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();
        String expiry = etExpiry.getText().toString().trim();


        if (name.isEmpty()) {
            etName.setError("Please enter an ingredient name");
            etName.requestFocus();
            return;
        }
        double quantity;
        try {
            quantity = Double.parseDouble(qtyText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Please enter a valid number");
            etQuantity.requestFocus();
            return;
        }
        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            etQuantity.requestFocus();
            return;
        }


        if (itemId == -1) {
            db.addPantryItem(name, quantity, unit, expiry);
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            db.updatePantryItem(itemId, name, quantity, unit, expiry);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}