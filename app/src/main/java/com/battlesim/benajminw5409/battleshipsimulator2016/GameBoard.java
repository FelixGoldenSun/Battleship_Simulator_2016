package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ben on 6/12/2016.
 */
public class GameBoard extends BaseActivity {

    static View attackingView;
    static View defendingView;

    static String[][] attackingArray = new String[10][10];
    static String[][] defendingArray = GameSetup.cellArray;
    int touch_row;
    int touch_col;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);

        attackingView = (View)findViewById(R.id.attackingView);
        defendingView = (View)findViewById(R.id.defendingView);


        attackingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    findRowCol( event.getY(), event.getX());
                    if(touch_row > 0 && touch_row < 11 && touch_col > 0 && touch_col < 11){
                        if(attackingArray[touch_row - 1][touch_col - 1].equals("")){
                            toastIt(getApplicationContext(), "Calculating...");
                            Attack(touch_row, touch_col);
                        }
                    }
                    return true;
                }
                return true;
            }
        });

        int row = 0;
        int col = 0;
        while(row < 10){
            while (col < 10){
                attackingArray[row][col] = "";
                col += 1;
            }
            col = 0;
            row += 1;
        }

        AttackingView.setArray(attackingArray);
        attackingView.invalidate();

        DefendingView.setArray(defendingArray);
        defendingView.invalidate();



    }

    public static void processAttack(Context context, String response){
        Log.i("Battle", response);
        try {

            JSONObject attack_data = new JSONObject(response);

            String attack_row_letter = attack_data.getString("row");

            int attack_row = rowLetterToNumber(attack_row_letter);
            int attack_col = attack_data.getInt("col");
            Boolean attack_hit = attack_data.getBoolean("hit");
            String playerShipSunk = attack_data.getString("user_ship_sunk");


            String defend_row_letter = attack_data.getString("comp_row");
            int defend_row = rowLetterToNumber(defend_row_letter);
            int defend_col = attack_data.getInt("comp_col");
            Boolean defend_hit = attack_data.getBoolean("comp_hit");
            String computerShipSunk = attack_data.getString("comp_ship_sunk");

            Log.i("GameBoard", "Attack row: " + attack_row + " col:" +attack_col + " hit: " + attack_hit);
            Log.i("GameBoard", "Defend row: " + defend_row + " col:" +defend_col + " hit: " + defend_hit);

            String winner = attack_data.getString("winner");

            if(winner.equals("")){

                String outcome = "";

                if(attack_hit){
                    attackingArray[attack_row - 1][attack_col] = "H";
                    outcome += "You Hit! :  ";

                }else{
                    attackingArray[attack_row - 1][attack_col] = "m";
                    outcome += "You missed : ";
                }

                AttackingView.setArray(attackingArray);
                attackingView.invalidate();

                if(defend_hit){
                    defendingArray[defend_row - 1][defend_col] = "H";
                    outcome += "Enemy Hit!";

                }else{
                    defendingArray[defend_row - 1][defend_col] = "m";
                    outcome += "Enemy missed";
                }

                toastIt(context, outcome);

                DefendingView.setArray(defendingArray);
                defendingView.invalidate();

                if(!playerShipSunk.equals("no")){
                    toastIt(context, "The enemy sunk your " + playerShipSunk + "!");
                }

                if(!computerShipSunk.equals("no")){
                    toastIt(context, "You sunk the enemy's " + computerShipSunk + "!");
                }

            }else{
                if(winner.equals("computer")){ //Api bug, displays wrong winner. User wins, outputs computer
                    toastIt(context, "You Win!");

                }else{
                    toastIt(context, "You Lose...");
                }

                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Attack(int row, int col ){
        String row_letter;
        if(row == 1){
            row_letter = "a";

        }else if (row == 2){
            row_letter = "b";
        }
        else if (row == 3){
            row_letter = "c";
        }
        else if (row == 4){
            row_letter = "d";
        }
        else if (row == 5){
            row_letter = "e";
        }
        else if (row == 6){
            row_letter = "f";
        }
        else if (row == 7){
            row_letter = "g";
        }
        else if (row == 8){
            row_letter = "h";
        }
        else if (row == 9){
            row_letter = "i";
        }
        else{
            row_letter = "j";
        }
        sr.setUrl(  attackUrl + gameId + "/attack/" + row_letter + "/" + col + ".json");
        sr.makeRequest("ATTACK");
    }

    private void findRowCol(float y, float x) {
        int cellWidth = DefendingView.cellWidth;
        touch_row = (int)(y / cellWidth); // empty cells start at row 1 : col 1
        touch_col = (int)(x / cellWidth);

        Log.i("Battle", "onTouch:" + touch_row + " : " + touch_col);

    }
}
