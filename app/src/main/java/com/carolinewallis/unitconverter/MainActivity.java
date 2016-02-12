package com.carolinewallis.unitconverter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class MainActivity extends AppCompatActivity {

    private EditText numberToBeConvertedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        numberToBeConvertedEditText = (EditText)findViewById(R.id.num_to_be_converted_edit_text);

        //put units into an array
        String[] unitsToConvert = {"inches \u2192 mm", "lb mass → kg", "lb force → kN",
                "lbs/ft\u00b2 → kN/m\u00b2", "Fahrenheit → Celsius", "mm → inches",
                "kg → lb mass","kN → lb force", "kN/m² → lbs/ft²", "Celsius → Fahrenheit"};

        //made adapter so the array can work with the list
       /*ListAdapter theAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, unitsToConvert);*/

        ListAdapter theAdapter = new ArrayAdapter<String>(this,
                R.layout.row_layout, R.id.textForList, unitsToConvert);

       //get the view object that we convert to a ListView, declared final b/c used in inner method
        final ListView unitsListView = (ListView) findViewById(R.id.unitsListView);

        //send the data (from array) to the ListView
        unitsListView.setAdapter(theAdapter);

        //catch clicks on the list items
        unitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Will get the text.
                //String conversionSelected = (String)(unitsListView.getItemAtPosition(position));
                //Will get the numerical position.
                Long conversionSelectedPosition = (unitsListView.getItemIdAtPosition(position));

                //location of EditText containing number to be converted
                //numberToBeConvertedEditText = (EditText) findViewById(R.id.num_to_be_converted_edit_text);

                //Make sure EditText is not empty
                String numberToBeConvertedAsString = numberToBeConvertedEditText.getText().toString();

                //if the EditText is empty
                if (numberToBeConvertedAsString.matches("")) {
                    Toast.makeText(MainActivity.this, "Enter a number to be converted", Toast.LENGTH_LONG).show();
                } else {

                    //If there is a number then convert from String to Double
                    Double numberToBeConverted = Double.parseDouble(numberToBeConvertedAsString);//numberToBeConvertedEditText.getText().toString());

                    //call method to calculate the conversion and get units involved as strings
                    String results[] = convertNumber(conversionSelectedPosition, numberToBeConverted);

                    //String message = "Test " + results[0] + " " + results[1] + " " + results[2];
                    //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                    //Find each TextView and send the results to be displayed
                    TextView convertedResultTextView = (TextView) findViewById(R.id.converted_result_text_view);
                    convertedResultTextView.setText(results[0]);

                    TextView unitFromTextView = (TextView) findViewById(R.id.unit_from_text_view);
                    unitFromTextView.setText(results[1]);

                    TextView unitToTextView = (TextView) findViewById(R.id.unit_to_text_view);
                    unitToTextView.setText(results[2]);
                }
            }
        });

    }

    //Make the conversion, both position and number to be converted are sent
    public String[] convertNumber (Long conversionSelectedPosition, Double numberToBeConverted){

        Double result = null;
        String unitOne = "";
        String unitTwo = "";

            //Determine which conversion was selected
            String selectedPosition = Long.toString(conversionSelectedPosition);

            //Determine which conversion was requested to determine units
            //Make the conversion and round to appropriate number decimal places
            switch (selectedPosition) {
                case "0":
                    result = Math.round((numberToBeConverted * 25.4) * 1000) / 1000D;
                    unitOne = "inches";
                    unitTwo = "mm";
                    break;
                case "1":
                    result = Math.round((numberToBeConverted * 0.45359237) * 1000) / 1000D;
                    unitOne = "lb mass";
                    unitTwo = "kg";
                    break;
                case "2":
                    result = Math.round((numberToBeConverted / 224.8089) * 1000) / 1000D;
                    unitOne = "lb force";
                    unitTwo = "kN";
                    break;
                case "3":
                    result = Math.round((numberToBeConverted / 20.8854) * 1000) / 1000D;
                    unitOne = "lbs/ft²";
                    unitTwo = "kN/m²";
                    break;
                case "4":
                    result = Math.round(((numberToBeConverted - 32.0) * (5.0 / 9.0)) * 10) / 10D;
                    unitOne = "Fahrenheit";
                    unitTwo = "Celsius";
                    break;
                case "5":
                    result = Math.round((numberToBeConverted / 25.4) * 1000) / 1000D;
                    unitOne = "mm";
                    unitTwo = "inches";
                    break;
                case "6":
                    result = Math.round((numberToBeConverted / 0.45359237) * 1000) / 1000D;
                    unitOne = "kg";
                    unitTwo = "lb mass";
                    break;
                case "7":
                    result = Math.round((numberToBeConverted * 224.8089) * 1000) / 1000D;
                    unitOne = "kN";
                    unitTwo = "lb force";
                    break;
                case "8":
                    result = Math.round((numberToBeConverted * 20.8854) * 1000) / 1000D;
                    unitOne = "kN/m²";
                    unitTwo = "lbs/ft²";
                    break;
                case "9":
                    result = Math.round((numberToBeConverted * (9.0 / 5.0) + 32) * 1000) / 1000D;
                    unitOne = "Celsius";
                    unitTwo = "Fahrenheit";
                    break;
            }

        //convert result from decimal to string, then return all strings in an array
        String finalResult = Double.toString(result);
        return new String[]{finalResult, unitOne, unitTwo};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editTextClick(View view) {
        numberToBeConvertedEditText.setText("");
        TextView convertedResultTextView = (TextView) findViewById(R.id.converted_result_text_view);
        convertedResultTextView.setText("");
        TextView unitFromTextView = (TextView) findViewById(R.id.unit_from_text_view);
        unitFromTextView.setText("");
        TextView unitToTextView = (TextView) findViewById(R.id.unit_to_text_view);
        unitToTextView.setText("");

    }
}
