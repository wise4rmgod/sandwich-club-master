package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME = "name";
    private final static String MAINNAME = "mainName";
    private final static String ALSOKNOWNAS = "alsoKnownAs";
    private final static String PLACEOFORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject(NAME);
            String mainNamejson = name.getString(MAINNAME);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSOKNOWNAS);
            List<String> alsoKnownAs = convertToListFromJsonArray(JSONArrayAlsoKnownAs);


            String imagejson = mainJsonObject.getString(IMAGE);
            String descriptionjson = mainJsonObject.getString(DESCRIPTION);
            String placeOfOriginjson = mainJsonObject.optString(PLACEOFORIGIN);


            JSONArray JSONArrayIngredients = mainJsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredientsjson = convertToListFromJsonArray(JSONArrayIngredients);

            return new Sandwich(mainNamejson, alsoKnownAs, placeOfOriginjson, descriptionjson, imagejson, ingredientsjson);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());
               // Run a loop
        for (int h = 0; h < jsonArray.length(); h++) {
            list.add(jsonArray.getString(h));
        }

        return list;
    }
}
