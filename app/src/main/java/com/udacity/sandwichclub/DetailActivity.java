package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView AlsoKnown;
    private TextView AlsoKnownLabel;
    private TextView Origin;
    private TextView OriginLabel;
    private TextView Description;
    private TextView Ingredient;
    private ImageView Sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Sandwich = findViewById(R.id.image);

        Description = findViewById(R.id.description);
        Ingredient = findViewById(R.id.ingredients);
        Origin = findViewById(R.id.origin);
        AlsoKnown = findViewById(R.id.also_known);
        OriginLabel = findViewById(R.id.placeOfOrigin_label);

        AlsoKnownLabel = findViewById(R.id.alsoKnownAs_label);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwichesarray = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwichesarray[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            //Checks for Sandwich data unavailability
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        //Toast error using the R.string.detail_error_message in string
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        // set Text to alsoKnown(TextView)
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            AlsoKnown.setText(stringBuilder.toString());
        } else {
            AlsoKnown.setVisibility(View.GONE);
            AlsoKnownLabel.setVisibility(View.GONE);
        }

        // set Text to origin(Textiew)
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            Origin.setVisibility(View.GONE);
            OriginLabel.setVisibility(View.GONE);
        } else {
            Origin.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to description(TextView)
        Description.setText(sandwich.getDescription());

        // set Text to ingredient(TextView)
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2022");
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append("\u2022");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            Ingredient.setText(stringBuilder.toString());
        }

        // display the image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(Sandwich);
    }
}