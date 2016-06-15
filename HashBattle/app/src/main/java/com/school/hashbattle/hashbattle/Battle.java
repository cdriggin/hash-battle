package com.school.hashbattle.hashbattle;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Battle extends AppCompatActivity {
    private HealthBar leftHealth;
    private HealthBar rightHealth;
    private String leftHash;
    private String rightHash;

    private static TwitterStatusListener twitterListener;
    private TextView textBox;
    private TextView leftHashLabel;
    private TextView rightHashLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        leftHealth = (HealthBar) findViewById(R.id.leftHealth);
        rightHealth = (HealthBar) findViewById(R.id.rightHealth);

        leftHashLabel = (TextView) findViewById(R.id.leftHashLabel);
        rightHashLabel = (TextView) findViewById(R.id.rightHashLabel);
        textBox = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        leftHash = "#" + intent.getStringExtra("PLAYER_ONE_HASHTAG");
        rightHash = "#" + intent.getStringExtra("PLAYER_TWO_HASHTAG");

        leftHashLabel.setText(leftHash);
        rightHashLabel.setText(rightHash);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (twitterListener == null)
            twitterListener = new TwitterStatusListener(leftHealth, rightHealth, leftHash, rightHash);
        else
            twitterListener.reinitialize(leftHealth, rightHealth, leftHash, rightHash);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        leftHealth.initialize(this);
        rightHealth.initialize(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        twitterListener.stop();
    }


    public void stopBattle() {
        final String victoryText = rightHealth.isDepleted() ? leftHash + "\nwins!" : rightHash + "\nwins!";
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textBox.setText(victoryText);
                twitterListener.stop();
            }
        });

    }
}
