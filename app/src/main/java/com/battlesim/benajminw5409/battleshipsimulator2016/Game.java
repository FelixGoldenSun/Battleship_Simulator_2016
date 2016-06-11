package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

    static String[][] cellArray = new String[10][10];

    static View gameGridView;

    static String status;
    static String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        tvGameId = (TextView) findViewById(R.id.tvGameId);
        spAddShips = (Spinner) findViewById(R.id.spAddShips);
        spAddRows = (Spinner) findViewById(R.id.spAddRows);
        spAddCols = (Spinner) findViewById(R.id.spAddCols);
        spAddDirections = (Spinner) findViewById(R.id.spAddDirections);
        gameGridView = (View)findViewById(R.id.gameGridView);

        spAddShips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String shipName = spAddShips.getSelectedItem().toString();
                shipName = shipName.split("\\(")[0];

                Log.i("Battle", "addshipval: " + shipName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gameGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Log.i("Battle", "onTouch:" + event.getX() + " : " + event.getY());
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i("Battle", "onTouch:" + event.getX() + " : " + event.getY());
                    findRowCol( event.getX(), event.getY());
                    return true;
                }
                return true;
            }
        });

        int row = 0;
        int col = 0;
        while(row < 10){
            while (col < 10){
                cellArray[row][col] = "";
                col += 1;
            }
            col = 0;
            row += 1;
        }

        BoardView.setArray(cellArray);
        gameGridView.invalidate();

        Log.i("Battle", Arrays.deepToString(cellArray));

        tvGameId.setText("ID: " + gameId);
        SetupGame();


    }

    private void findRowCol(float x, float y) {
        int cellWidth = BoardView.cellWidth;
        int row = (int)(x / cellWidth);
        int col = (int)(y / cellWidth);

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

    public void AddShip(String shipName, String row, String col, String direction) {
        sr.setUrl(  addShipsUrl + gameId + "/add_ship/" + shipName + "/" + row + "/" + col + "/" + direction + ".json");
        sr.makeRequest("ADDSHIP");
    }

    public static void  processAddShip(String response){
        Log.i("Battle", response);
        try {

            JSONObject shi = new JSONObject(response);
            status = null;
            error = null;

            try {
                status = shi.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                error = shi.getString("error");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (status != null) {
                Log.i("addShip", status);



            } else {
                Log.i("addShip", error);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addShipOnClick(View v){

        String shipName = spAddShips.getSelectedItem().toString();
        shipName = shipName.split("\\(")[0];

        Log.i("Battle", "addshipval: " + shipName);
        String row = spAddRows.getSelectedItem().toString();
        String col = spAddCols.getSelectedItem().toString();
        String direction = spAddDirections.getSelectedItem().toString();

        AddShip(shipName, row, col, direction);

    }


}
