package com.school.hashbattle.hashbattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


/**
 * Main Kicker class for the start of the hash Battle.
 * Gathers the two fields from the end user and validates that
 * they has values before moving to the next page.
 */
public class MainActivity extends AppCompatActivity {

    //The Player One's Hashtag for battling
    EditText mPlayerOneTag;

    //The Player Two's Hashtag for battling
    EditText mPlayerTwoTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get fields you will need to store as members*/
        mPlayerOneTag = (EditText) findViewById(R.id.playerOneTag);
        mPlayerTwoTag = (EditText) findViewById(R.id.playerTwoTag);

        /* Set Text Watcher listener for validation */
        String regex = getString(R.string.validation_regex);
        mPlayerOneTag.addTextChangedListener(new HashTagWatcher(mPlayerOneTag, regex));
        mPlayerTwoTag.addTextChangedListener(new HashTagWatcher(mPlayerTwoTag, regex));
    }

    /**
     * Method is called by Submit button in Layout page to fire the validation
     * on two text inputs and push their values to the next page.
     *
     * @param view
     */
    public void submitBattleStart(View view) {
        /* Fire Validation by changing the text to what it currently is */
        mPlayerOneTag.setText(mPlayerOneTag.getText());
        mPlayerTwoTag.setText(mPlayerTwoTag.getText());

        /* Check Validation */
        if (mPlayerOneTag.getError() == null && mPlayerTwoTag.getError() == null) {
            //Send the values over to the next page
            Intent intent = new Intent(this, Battle.class);
            intent.putExtra("PLAYER_ONE_HASHTAG", mPlayerOneTag.getText());
            intent.putExtra("PLAYER_TWO_HASHTAG", mPlayerTwoTag.getText());
            startActivity(intent);
        }
    }
}
