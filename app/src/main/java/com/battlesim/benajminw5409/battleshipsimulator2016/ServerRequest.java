package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ben on 5/31/2016.
 */
public class ServerRequest {

    String username, password, url, command;
    String serverResponse, serverError;
    RequestQueue queue;
    ServerRequests listener;

    public  ServerRequest(ServerRequests  _listener, String _command, String _username, String _password, String _url, RequestQueue _queue){
        username = _username;
        password = _password;
        url = _url;
        command = _command;
        queue = _queue;
        listener = _listener;
    }

    public void makeRequest(final String _command){
        command = _command;
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Log.i("Battleship", response);
                listener.ProcessResponse( _command, response );

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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // 10 seconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add( stringRequest );

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getServerResponse() {
        return serverResponse;
    }


    public String getServerError() {
        return serverError;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

   /* public ServerRequests getListener() {
        return listener;
    }

    public void setListener(ServerRequests listener) {
        this.listener = listener;
    }
    */
}
