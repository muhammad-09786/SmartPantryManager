package com.example.smartpantrymanager;

import java.util.Locale;

public class IngredientMatcher {


    public static String normalizeName(String raw) {
        if (raw == null) return "";
        String n = raw.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
        if (n.endsWith("ies") && n.length() > 3) {
            n = n.substring(0, n.length() - 3) + "y";
        } else if (n.endsWith("oes") && n.length() > 3) {
            n = n.substring(0, n.length() - 2);
        } else if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 1) {
            n = n.substring(0, n.length() - 1);
        }
        return n;
    }


    public static double toBaseQty(double qty, String unit) {
        switch (unit.trim().toLowerCase(Locale.ROOT)) {
            case "kg":  return qty * 1000;
            case "l":   return qty * 1000;
            case "cup": case "cups": return qty * 240;
            case "tbsp": return qty * 15;
            case "tsp":  return qty * 5;
            default:     return qty;
        }
    }


    public static String baseUnit(String unit) {
        switch (unit.trim().toLowerCase(Locale.ROOT)) {
            case "kg": case "g":
                return "g";
            case "l": case "ml": case "cup": case "cups": case "tbsp": case "tsp":
                return "ml";
            default:
                return "pcs";
        }
    }


    public static String key(String name, String unit) {
        return normalizeName(name) + "|" + baseUnit(unit);
    }
}