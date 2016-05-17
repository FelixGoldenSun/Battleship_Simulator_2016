package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String username = "sdf";
        final String password = "sdfa";


        RequestQueue queue = Volley.newRequestQueue( this );

        String stringRequest = new StringRequest( DownloadManager.Request.Method.GET, "http://www.google.com", new Responce.Listener<String>(){
            @Override
            public void onResponce(String responce){
                Log.i("Battleship", responce);
            }
        },
        new Responce.ErrorListener(){
            @override
            public void onErrorResponce(VolleyError e){
                Log.i("Battleship", e);
            }
        }){
            @override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @override
            public Map<String, String>, getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                String credentials = username + ";" + password;
                String auth = "Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP);

            }
        };

        queue.add( stringRequest);

    }
}
