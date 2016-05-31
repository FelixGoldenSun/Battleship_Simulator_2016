package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String loginUrl = "http://battlegameserver.com/api/v1/login";
    String username = "benawalls@gmail.com";
    String password = "DontTell";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue( this );

        StringRequest stringRequest = new StringRequest( Request.Method.GET, loginUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Log.i("Battleship", response);
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError e){
                Log.i("Battleship", e.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> headers = new HashMap<String, String>();
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP);
                Log.i("Battle", auth);
                headers.put("Authorization", auth);
                return headers;

            }
        };

        queue.add( stringRequest );

    }
}
