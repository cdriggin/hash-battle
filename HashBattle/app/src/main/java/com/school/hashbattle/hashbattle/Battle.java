package com.school.hashbattle.hashbattle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Battle extends AppCompatActivity {
    private HealthBar leftHealth;
    private HealthBar rightHealth;

    AsyncTask<Integer, Void, Void> leftHealthHitter;
    AsyncTask<Integer, Void, Void> rightHealthHitter;
    private TextView textBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        leftHealth = (HealthBar) findViewById(R.id.leftHealth);
        rightHealth = (HealthBar) findViewById(R.id.rightHealth);
        textBox = (TextView) findViewById(R.id.textView);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        leftHealth.initializeHealth(this);
        rightHealth.initializeHealth(this);

        leftHealthHitter = startHitting(leftHealth);
        leftHealthHitter.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1);

        rightHealthHitter = startHitting(rightHealth);
        rightHealthHitter.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1);
    }

    private AsyncTask<Integer, Void, Void> startHitting(final HealthBar healthBar) {
        final Random random = new Random();
        return new AsyncTask<Integer, Void, Void>(){
            @Override
            protected Void doInBackground(Integer... params) {
                while (true) {
                    publishProgress();
                    if (isCancelled())
                        return null;

                    int sleepInterval = random.nextInt(250);

                    try {
                        Thread.sleep(sleepInterval);
                    }
                    catch (Exception e) {

                    }
                }
            }

            protected void onProgressUpdate(Void... progress) {
                healthBar.takeHit();
            }
        };
    }

    public void stopHitting() {
        leftHealthHitter.cancel(true);
        rightHealthHitter.cancel(true);

        String victoryText = leftHealth.isDepleted() ? "Right wins!" : "Left Wins!";
        textBox.setText(victoryText);
    }
}
