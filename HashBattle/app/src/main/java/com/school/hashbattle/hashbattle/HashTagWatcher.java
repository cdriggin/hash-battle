package com.school.hashbattle.hashbattle;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class follows the TextWatcher Interface and is used to verify
 * that the given values it is watching are valid hash tags
 * via regex validation.
 */
public class HashTagWatcher implements TextWatcher {

    //The EditText we are watching (so errors can be added)
    private EditText mWatchingTag;

    //The Regex to validate with
    private String mRegex;

    /**
     * Constructor that accepts and sets private member for EditText we are
     * validating.
     *
     * @param mWatchingTag the tag we are listening for.
     * @param mRegex       the Regex we are using to validate
     */
    public HashTagWatcher(EditText mWatchingTag, String mRegex) {
        super();
        this.mWatchingTag = mWatchingTag;
        this.mRegex = mRegex;
    }

    /**
     * Method fires when the Text changes and verifies the entries are not empty
     * and pass the regex validation.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Matcher matcher = Pattern.compile(mRegex).matcher(s);
        if (s.toString() == null || s.toString().equals("")) {
            mWatchingTag.setError("Entry is required");
        } else if (!matcher.find()) {
            mWatchingTag.setError("Must only be numeric and alpha characters");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //No Logic here, required for interface
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //No logic here, required for interface
    }
}


