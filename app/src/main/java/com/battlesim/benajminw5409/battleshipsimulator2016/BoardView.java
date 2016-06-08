package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Ben on 6/7/2016.
 */
public class BoardView extends ImageView {

    Integer screenHeight, screenWidth;
    Paint paint;

    public BoardView( Context context, AttributeSet attrs ){
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeWidth( 5 );
        paint.setColor( Color.BLUE );
        paint.setStyle( Paint.Style.FILL_AND_STROKE );
    }

    @Override
    protected void onDraw( Canvas canvas ){
        screenHeight = canvas.getHeight();
        screenWidth = canvas.getWidth();

        paint.setColor( Color.CYAN);
        canvas.drawRect(0,0,screenWidth, screenWidth, paint);

        paint.setColor(Color.BLACK);
        canvas.drawLine(2, 0, 0, screenWidth, paint);

        int cellWidth = screenWidth / 11;
        paint.setTextSize( cellWidth ); //TODO chage to be based on the screenWidth.
        for(int i = 0;i < 12; i++){
            canvas.drawLine((i * cellWidth), 0, (i * cellWidth), screenWidth, paint); //X
            canvas.drawLine(0, (i * cellWidth), screenWidth, (i * cellWidth), paint); //Y
        }

        canvas.drawText("A", 20, (2*cellWidth)-20, paint);
    }
}
