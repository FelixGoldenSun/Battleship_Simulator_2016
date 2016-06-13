package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Ben on 5/31/2016.
 */
public class BaseActivity extends AppCompatActivity {

    String loginUrl = "http://battlegameserver.com/api/v1/login";
    String getAllUsersUrl = "http://battlegameserver.com/api/v1/all_users.json";
    String logoutUrl = "http://battlegameserver.com/api/v1/logout.json";
    String startGameUrl = "http://battlegameserver.com/api/v1/challenge_computer.json ";
    String availableShipsUrl = "http://battlegameserver.com/api/v1/available_ships.json ";
    String availableDirectionsUrl = "http://battlegameserver.com/api/v1/available_directions.json";
    String addShipsUrl = "http://battlegameserver.com/api/v1/game/";
    String attackUrl = "http://battlegameserver.com/api/v1/game/";
    static User user;
    Boolean loggedIn = false;
    static int gameId = -1; //negative to detect if the program broke
    static ServerRequest sr;

    public static void toastIt(Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static int rowLetterToNumber(String letter){
        int return_int;

        if(letter.equals("a")){
            return_int = 1;

        }else if (letter.equals("b")){
            return_int = 2;
        }
        else if (letter.equals("c")){
            return_int = 3;
        }
        else if (letter.equals("d")){
            return_int = 4;
        }
        else if (letter.equals("e")){
            return_int = 5;
        }
        else if (letter.equals("f")){
            return_int = 6;
        }
        else if (letter.equals("g")){
            return_int = 7;
        }
        else if (letter.equals("h")){
            return_int = 8;
        }
        else if (letter.equals("i")){
            return_int = 9;
        }
        else{
            return_int = 10;
        }

        return return_int;
    }

}
