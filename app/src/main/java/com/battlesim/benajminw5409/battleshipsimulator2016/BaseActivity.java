package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.support.v7.app.AppCompatActivity;

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
    static User user;
    Boolean loggedIn = false;
    static int gameId = -1; //negative to detect if the program broke
    static ServerRequest sr;
}
