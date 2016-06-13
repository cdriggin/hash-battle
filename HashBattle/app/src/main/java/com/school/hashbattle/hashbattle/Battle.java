package com.school.hashbattle.hashbattle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Battle extends AppCompatActivity {
    private HealthBar leftHealth;
    private HealthBar rightHealth;
    private String leftHash;
    private String rightHash;

    TwitterStatusListener leftListener;
    TwitterStatusListener rightListener;
    private TextView textBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        leftHealth = (HealthBar) findViewById(R.id.leftHealth);
        rightHealth = (HealthBar) findViewById(R.id.rightHealth);
        textBox = (TextView) findViewById(R.id.textView);

        leftHash = "#Periscope";
        rightHash = "#OrlandoShooting";
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {

        leftHealth.initialize(this);
        rightHealth.initialize(this);

        leftListener = new TwitterStatusListener(leftHealth, leftHash);
        rightListener = new TwitterStatusListener(rightHealth, rightHash);
    }

    public void stopBattle() {
        final String victoryText = leftHealth.isDepleted() ? leftHash + "\nwins!" : rightHash + "\nwins!";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textBox.setText(victoryText);
            }
        });
        leftListener.stop();
        rightListener.stop();
    }
}
