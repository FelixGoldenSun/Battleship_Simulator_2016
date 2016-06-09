package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

interface ServerRequests{
    public void ProcessResponse(String command, String response);
}

public class MainActivity extends BaseActivity implements ServerRequests{

    EditText etUsername;
    EditText etPassword;
    EditText etExistingId;
    Button btnLogin;
    Button btnUserPreferences;
    Button btnStartGame;
    String username;
    String password;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etExistingId = (EditText) findViewById(R.id.etExistingId);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnUserPreferences = (Button) findViewById(R.id.btnUserPreferences);
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        listView = (ListView) findViewById(R.id.listView);
        btnUserPreferences.setEnabled(false);
        btnStartGame.setEnabled(false);

        RequestQueue queue = Volley.newRequestQueue( this );
        sr = new ServerRequest(this, "LOGIN", username, password, loginUrl, queue);
        ImageLoader imageLoader = new ImageLoader(queue, new LruBitmapCache(LruBitmapCache.getCacheSize(this)));

     /*  // Defined Array values to show in ListView
        String[] values = new String[] {
                "Dave",
                "Joe",
                "Sally",
                "Sharon",
                "Karen",
                "Tom",
                "Lynn",
                "Scott"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);*/

    }

    public void clickLogin( View v){
        if(!loggedIn){
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            sr.setUsername(username);
            sr.setPassword(password);

            sr.setUrl( loginUrl );
            sr.makeRequest("LOGIN");

        }else{
            sr.setUrl( logoutUrl );
            sr.makeRequest("LOGOUNT");
            btnUserPreferences.setEnabled(false);
            btnLogin.setText("Login");
            etUsername.setEnabled(true);
            etPassword.setEnabled(true);
            btnStartGame.setEnabled(false);
            loggedIn = false;

        }
    }

    private void processLogin( String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            user = new User();
            user.setId(jsonObject.getInt("id"));
            user.setFirst_name( jsonObject.getString("first_name"));
            user.setLast_name( jsonObject.getString("last_name"));
            user.setOnline(jsonObject.getBoolean("online"));
            loggedIn = true;
            btnUserPreferences.setEnabled(true);
            btnLogin.setText("Log Out");
            etUsername.setEnabled(false);
            etPassword.setEnabled(false);
            btnStartGame.setEnabled(true);


            Log.i("Battle", user.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  void processGetAllUsers(String response){
        Log.i("Battle", response);
        try {
            JSONArray users = new JSONArray(response);
            ArrayList<String> usersArray = new ArrayList<String>();
            for (int i = 0; i < users.length(); i++) {
                JSONObject jsonobject = users.getJSONObject(i);
                String user_info = jsonobject.getString("first_name") + " " + jsonobject.getString("last_name") + "; available: " + jsonobject.getString("available");
                usersArray.add(user_info);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, usersArray);
                listView.setAdapter(adapter);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void startGameClick(View v){
        sr.setUrl( startGameUrl );
        sr.makeRequest("STARTGAME");
    }

    public void startExistingGameClick(View v){
        gameId = Integer.valueOf(etExistingId.getText().toString());
        startActivity( new Intent( this, Game.class));
    }

    private void processStartGame(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            gameId = jsonObject.getInt("game_id");
            Log.i("Battle", "Game ID: " + gameId);
            startActivity( new Intent( this, Game.class));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gotoPreferences( View v ){
        if(loggedIn){
            startActivity( new Intent( this, BattlePrefs.class));
        }
    }

    public void getAllUsersOnClick(View v){
        sr.setUrl( getAllUsersUrl );
        sr.makeRequest("GETALLUSERS");
    }


    @Override
    public void ProcessResponse( String command, String response){
        switch(command){
            case "LOGIN" :
                Log.i("Battle", "LOGIN----" + response);
                processLogin(response);
                break;

            case "GETALLUSERS" :
                Log.i("Battle", "GETALLUSERS----" + response);
                processGetAllUsers(response);
                break;

            case "GETSHIPS" :
                Log.i("Battle", "GETSHIPS----" + response);
                Game.processGetShips(getApplicationContext(), response);
                break;

            case "GETDIRECTIONS" :
                Log.i("Battle", "GETDIRECTIONS----" + response);
                Game.processGetDirections(getApplicationContext(), response);
                break;

            case "STARTGAME" :
                Log.i("Battle", "STARTGAME----" + response);
                processStartGame(response);
                break;

            default : break;
        }
    }
}
