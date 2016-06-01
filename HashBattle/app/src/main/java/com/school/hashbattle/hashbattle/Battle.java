package com.school.hashbattle.hashbattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Battle extends AppCompatActivity {
    private HealthBar leftHealth;
    private HealthBar rightHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        leftHealth = (HealthBar) findViewById(R.id.leftHealth);
        rightHealth = (HealthBar) findViewById(R.id.rightHealth);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        leftHealth.initializeHealth();
        rightHealth.initializeHealth();
    }
}
