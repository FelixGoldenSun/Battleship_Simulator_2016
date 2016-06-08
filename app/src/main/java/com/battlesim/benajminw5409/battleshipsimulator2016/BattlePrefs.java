package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ben on 5/31/2016.
 */
public class BattlePrefs extends BaseActivity {

    EditText etFirstName;
    EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battleprefs);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);

        etFirstName.setText(user.getFirst_name());
        etLastName.setText(user.getLast_name());




    }
}
