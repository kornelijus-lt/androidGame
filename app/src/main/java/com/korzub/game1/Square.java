package com.korzub.game1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

public class Square  {

    private int color;


    private int y;
    private int x;

    private boolean hasShip;

    private int height;
    private int width;

    private Paint paint;
    private Rect rect;

    private View currentView;

    private int identity;


    public Square(Context context,int color,int startY,int startX,int boardDim,View currentView,Canvas canvas,int identity){


        paint=new Paint();
        rect=new Rect();

        this.color=color;

        this.y=startY;
        this.x=startX;

        /*
        this.height = this.getScreenHeight();
        this.width = this.getScreenWidth();
        */

        height=currentView.getHeight();
        width=currentView.getWidth();

        this.height=height/boardDim;
        this.width=width/boardDim;

        this.currentView=currentView;


        this.identity=identity;

        paintSquare(canvas);


    }

    //Colors: 0-Cyan(Hidden),1-Green(Friendly),2-Red(Dead),3-Yellow(Hit/Enemy),4-Blue(Clear),5-Magenta(Confirm)
    public void paintSquare(Canvas canvas){

        if(this.color==0){
            paint.setColor(Color.CYAN);
        }
        else if(this.color==1){
            paint.setColor(Color.GREEN);
        }
        else if(this.color==2){
            paint.setColor(Color.RED);
        }
        else if(this.color==3){
            paint.setColor(Color.YELLOW);
        }
        else if(this.color==4){
            paint.setColor(Color.BLUE);
        }
        else if(this.color==5){
            paint.setColor(Color.MAGENTA);
        }


        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x*width,y*height,x*width+width,y*height+height,paint);

        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        canvas.drawRect(x*width,y*height,x*width+width,y*height+height,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(15);

        String text=Integer.toString(this.identity);

        paint.setTextSize(100);
        //canvas.drawText(text, x*width,y*height+height,paint);



    }

    public void setColor(int color){
        this.color=color;
    }
    public int getColor(){
        return this.color;
    }

    public void setIdentity(int identity){this.identity=identity; }
}
