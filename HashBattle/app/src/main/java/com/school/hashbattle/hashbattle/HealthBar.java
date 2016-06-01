package com.school.hashbattle.hashbattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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

    public HealthBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeHealth();
    }

    public void initializeHealth() {
        mDrawable = new ShapeDrawable(new RectShape());
        mDrawable.getPaint().setColor(Color.GREEN);

        totalHeight = this.getHeight();
        totalWidth = this.getWidth();

        mDrawable.setBounds(0, 0, totalWidth, totalHeight);
    }

    public HealthBar(Context context) {
        super(context);
        initializeHealth();
    }

    public HealthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeHealth();
    }

    public void setHealth(double percentHealth) {
        //Change health....

    }



    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
