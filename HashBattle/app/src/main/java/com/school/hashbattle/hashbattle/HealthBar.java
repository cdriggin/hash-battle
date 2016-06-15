package com.school.hashbattle.hashbattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nate on 5/31/16.
 */
public class HealthBar extends View {
    private ShapeDrawable mDrawable;
    private int totalHeight;
    private int totalWidth;
    private int currentHealth;
    private Battle battle;
    private boolean created;

    public HealthBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(Battle battle) {
        this.battle = battle;
        currentHealth = 100;
        mDrawable = mDrawable == null ? new ShapeDrawable(new RectShape()) : mDrawable;
        created = true;
        mDrawable.getPaint().setColor(Color.GREEN);

        totalHeight = this.getHeight();
        totalWidth = this.getWidth();

        mDrawable.setBounds(0, 0, totalWidth, totalHeight);
    }

    public HealthBar(Context context) {
        super(context);
    }

    public HealthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void takeHit() {
        if (isDepleted()) {
            battle.stopBattle();
        } else {

            currentHealth--;

            if (currentHealth < 50)
                mDrawable.getPaint().setColor(Color.YELLOW);

            if (currentHealth < 15)
                mDrawable.getPaint().setColor(Color.RED);


            int healthBarTop = totalHeight - (currentHealth * totalHeight / 100);
            mDrawable.setBounds(0, healthBarTop, totalWidth, totalHeight);
            this.postInvalidate();
        }
    }



    protected void onDraw(Canvas canvas) {
        if (created)
            mDrawable.draw(canvas);
    }

    public boolean isDepleted() {
        return currentHealth <= 0;
    }
}
