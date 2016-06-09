package com.battlesim.benajminw5409.battleshipsimulator2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.reflect.Array;

/**
 * Created by Ben on 6/7/2016.
 */
public class BoardView extends ImageView {

    Integer screenHeight, screenWidth;
    Paint paint;
    String[] rowsArray;
    String[] colsArray;

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

        Rect bounds = new Rect();
        paint.getTextBounds("A", 0, 1, bounds);
        int height = bounds.height();
        int width = bounds.width();

        paint.setTextAlign( Paint.Align.CENTER);
        int textX = cellWidth / 2;
        int textY = cellWidth + ((cellWidth/2)-(height/2));


        for (String row : rowsArray = getResources().getStringArray(R.array.rows)) { //Adds row labels
            textY += cellWidth;
            canvas.drawText(row, textX, textY, paint);
        }

        textX = cellWidth / 2;
        textY = cellWidth + ( cellWidth / 2) - ( height / 2 );

        for (String col : colsArray = getResources().getStringArray(R.array.cols)) { //Adds col labels
            textX += cellWidth;
            canvas.drawText(col, textX, textY, paint);
        }
    }
}
