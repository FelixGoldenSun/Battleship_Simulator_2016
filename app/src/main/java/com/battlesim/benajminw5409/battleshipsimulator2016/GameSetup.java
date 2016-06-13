package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
public class GameSetup extends BaseActivity {

    TextView tvGameId;
    static Spinner spAddShips;
    static Spinner spAddRows;
    static Spinner spAddCols;
    static Spinner spAddDirections;


    static Map<String, Integer> shipsMap = new HashMap<String, Integer>();
    static ArrayList<String> shipsArray = new ArrayList<String>();
    static ArrayAdapter<String> shipSpinnerArrayAdapter;

    static Map<String, Integer> directionsMap = new HashMap<String, Integer>();
    static String[] directionsArray;
    static ArrayAdapter<String> directionSpinnerArrayAdapter;

    static String[][] cellArray = new String[10][10];

    static View gameGridView;

    static int shipLength;
    static String row;
    static String col;
    static String direction;

    static String status;
    static String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_setup);

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
                if(event.getAction() == MotionEvent.ACTION_UP){
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

        DefendingView.setArray(cellArray);
        gameGridView.invalidate();

        Log.i("Battle", Arrays.deepToString(cellArray));

        tvGameId.setText("ID: " + gameId);
        SetupGame();


    }

    private void findRowCol(float x, float y) {
        int cellWidth = DefendingView.cellWidth;
        int row = (int)(x / cellWidth);
        int col = (int)(y / cellWidth);

        Log.i("Battle", "onTouch:" + row + " : " + col);

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
                shipsArray.add((key + "(" + value + ")"));
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
        Log.i("Battle", availableDirectionsUrl);
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

    public void AddShip(String shipName, String row, String col, int direction) {
        sr.setUrl(  addShipsUrl + gameId + "/add_ship/" + shipName + "/" + row + "/" + col + "/" + direction + ".json");
        sr.makeRequest("ADDSHIP");
    }

    public static void  processAddShip(Context context, String response){
        Log.i("Battle", response);
        try {

            JSONObject shi = new JSONObject(response);
            status = null;
            error = null;

            try {
                status = shi.getString("status");
                Log.i("addShip", status);
                toastIt(context, status);

                int rowInt = rowLetterToNumber(row) - 1;
                int colInt = Integer.parseInt(col) - 1;

                int counter = 0;
                while (counter < shipLength){
                    cellArray[rowInt][colInt] = "S";

                    if("north".equals(direction)){
                        rowInt -= 1;

                    }else if ("south".equals(direction)){
                        rowInt += 1;

                    }else if ("east".equals(direction)){
                        colInt += 1;

                    }else if ("west".equals(direction)){
                        colInt -= 1;

                    }

                    counter += 1;
                }


                shipsArray.remove(spAddShips.getSelectedItem().toString());

                shipSpinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shipsArray);
                shipSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAddShips.setAdapter(shipSpinnerArrayAdapter);

                DefendingView.setArray(cellArray);
                gameGridView.invalidate();

                if (shipsArray.size() == 0){
                    Intent i = new Intent(context, GameBoard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                error = shi.getString("error");
                Log.i("addShip", error);
                toastIt(context, error);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addShipOnClick(View v){

        String shipName = spAddShips.getSelectedItem().toString();
        shipLength = shipsMap.get(shipName);
        shipName = shipName.split("\\(")[0];

        Log.i("Battle", "addshipval: " + shipName);
        row = spAddRows.getSelectedItem().toString();
        col = spAddCols.getSelectedItem().toString();
        direction = spAddDirections.getSelectedItem().toString();
        int directionInt = directionsMap.get(direction);
        Log.i("Direction", "direction: " + directionInt);

        AddShip(shipName, row, col, directionInt);

    }


}
