package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ben on 6/1/2016.
 */
public class Game extends BaseActivity {

    TextView tvGameId;
    static Spinner spAddShips;
    static Spinner spAddRows;
    static Spinner spAddCols;
    static Spinner spAddDirections;


    static Map<String, Integer> shipsMap = new HashMap<String, Integer>();
    static String[] shipsArray;
    static ArrayAdapter<String> shipSpinnerArrayAdapter;

    static Map<String, Integer> directionsMap = new HashMap<String, Integer>();
    static String[] directionsArray;
    static ArrayAdapter<String> directionSpinnerArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        tvGameId = (TextView) findViewById(R.id.tvGameId);
        spAddShips = (Spinner) findViewById(R.id.spAddShips);
        spAddRows = (Spinner) findViewById(R.id.spAddRows);
        spAddCols = (Spinner) findViewById(R.id.spAddCols);
        spAddDirections = (Spinner) findViewById(R.id.spAddDirections);

        spAddShips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Battle", spAddShips.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvGameId.setText("ID: " + gameId);
        SetupGame();

    }

    private void SetupGame(){

        GetShips();
        GetDirections();

    }

    public void GetShips() {
        sr.setUrl( availableShipsUrl );
        sr.makeRequest("GETSHIPS");
    }

    public  static  void processGetShips(Context context, String response){
        Log.i("Battle", response);
        try {
            JSONObject ships = new JSONObject(response);
            Iterator iter = ships.keys();
            while(iter.hasNext()){
                String key = (String)iter.next();
                int value = ships.getInt(key);
                shipsMap.put(key + "(" + value + ")", value);
                int size = shipsMap.keySet().size();
                shipsArray = new String[size];
                shipsArray = shipsMap.keySet().toArray(new String[0]);

                shipSpinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shipsArray);
                shipSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAddShips.setAdapter(shipSpinnerArrayAdapter);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GetDirections() {
        sr.setUrl( availableDirectionsUrl );
        sr.makeRequest("GETDIRECTIONS");
    }

    public static void  processGetDirections(Context context, String response){
        Log.i("Battle", response);
        try {
            JSONObject directions = new JSONObject(response);
            Iterator iter2 = directions.keys();
            while(iter2.hasNext()){
                String key2 = (String)iter2.next();
                int value2 = directions.getInt(key2);
                directionsMap.put(key2, value2);
                int size = directionsMap.keySet().size();
                directionsArray = new String[size];
                directionsArray = directionsMap.keySet().toArray(new String[0]);

                directionSpinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, directionsArray);
                directionSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAddDirections.setAdapter(directionSpinnerArrayAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addShipOnClick(View v){
        String shipName = spAddShips.getSelectedItem().toString();
        String row = spAddRows.getSelectedItem().toString();
        String col = spAddCols.getSelectedItem().toString();
    }
}
