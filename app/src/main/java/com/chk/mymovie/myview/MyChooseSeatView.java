package com.chk.mymovie.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chk.mymovie.R;

import java.util.ArrayList;

/**
 * Created by chk on 17-5-12.
 */

public class MyChooseSeatView extends View{
    public static final int SELECT = 1;
    public static final int CANCEL = 2;


    Paint paint;
    Paint paintGreen;
    Paint paintRed;
    Paint paintBlack;
    int width;
    int height;
    int detalX;
    int detalY;
    int pointX;
    int pointY;
    int row;    //行数
    int column; //列数
    int choosedCount;   //已选择的票数
    Handler handler;
    ArrayList<Integer> rows;
    ArrayList<Integer> columns;
    /**
     * 0表示未占座,-1表示已占座,1表示自己选的坐
     */
    int[][] seats = new int[6][8];

    public MyChooseSeatView(Context context) {
        super(context);
    }

    public MyChooseSeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        getWithAndHeight();
        setRowAndColumn(6,8);
        seats = new int[row][column];

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setAntiAlias(true);

        paintRed = new Paint();
        paintRed.setColor(Color.RED);
        paintRed.setAntiAlias(true);

        paintBlack = new Paint();
        paintBlack.setTextSize(50);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setAntiAlias(true);

        rows = new ArrayList<>();
        rows.add(2);
        columns = new ArrayList<>();
        columns.add(3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawChoosedSeat(canvas);
        Log.e("MyChooseSeatView",width+" "+height);
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawColor(Color.CYAN);
        for (int i=0; i<9; i++) {
            if (i<7) {  //绘制横线
                canvas.drawLine(0,detalY*i,width,detalY*i,paint);
            }
            canvas.drawLine(detalX*i,0,detalX*i,height-detalY,paint);
        }
        canvas.drawCircle(detalX+detalX/2,detalY*6+detalY/4,detalX/6,paintRed);
        canvas.drawRect(detalX+detalX/6,detalY*6+detalY*2/5,detalX+5*detalX/6,detalY*6+3*detalY/4,paintRed);
        canvas.drawText("已选",detalX*2,detalY*6+3*detalY/5,paintBlack);

        canvas.drawCircle(detalX*5+detalX/2,detalY*6+detalY/4,detalX/6,paintGreen);
        canvas.drawRect(detalX*5+detalX/6,detalY*6+detalY*2/5,detalX*5+5*detalX/6,detalY*6+3*detalY/4,paintGreen);
        canvas.drawText("我的",detalX*6,detalY*6+3*detalY/5,paintBlack);
    }

    public void drawChoosedSeat(Canvas canvas) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.diver);

//        for (int i=0; i<rows.size(); i++) {
//            canvas.drawRect(detalX*rows.get(i),detalY*columns.get(i),detalX*(rows.get(i)+1),detalY*(columns.get(i)+1),paintGreen);
//            canvas.drawCircle(detalX*rows.get(i)+detalX/2,detalY*columns.get(i)+detalY/2,detalX/3,paintGreen);
//            canvas.drawCircle(detalX*rows.get(i)+detalX/2,detalY*columns.get(i)+detalY/4,detalX/6,paintGreen);
//            canvas.drawRect(detalX*rows.get(i)+detalX/6,detalY*columns.get(i)+detalY*2/5,detalX*rows.get(i)+5*detalX/6,detalY*columns.get(i)+3*detalY/4,paintGreen);
//        }

        for (int i=0; i<row;i++) {
            for (int j=0; j<column; j++) {
                if (seats[i][j] == 1) {
                    canvas.drawCircle(detalX*j+detalX/2,detalY*i+detalY/4,detalX/6,paintGreen);
                    canvas.drawRect(detalX*j+detalX/6,detalY*i+detalY*2/5,detalX*j+5*detalX/6,detalY*i+3*detalY/4,paintGreen);
                } else if (seats[i][j] == -1) {
                    canvas.drawCircle(detalX*j+detalX/2,detalY*i+detalY/4,detalX/6,paintRed);
                    canvas.drawRect(detalX*j+detalX/6,detalY*i+detalY*2/5,detalX*j+5*detalX/6,detalY*i+3*detalY/4,paintRed);
                }

            }
        }

//
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getWithAndHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
                int pointX = (int) event.getX();
                int pointY = (int) event.getY();
                if (pointY >= height-detalY) //超出边界,直接返回
                    return true;
                int choosedRow = pointY/detalY;
                int choosedColumn = pointX/detalX;
                Message message = new Message();

                if (seats[choosedRow][choosedColumn] == 0) {
                    seats[choosedRow][choosedColumn] = 1;
                    choosedCount++;
                    message.what = SELECT;
                } else if (seats[choosedRow][choosedColumn] == 1) {
                    seats[choosedRow][choosedColumn] = 0;
                    choosedCount--;
                    message.what = CANCEL;
                } else if (seats[choosedRow][choosedColumn] == -1) {
                    return true;
                }
                invalidate();
                message.obj = choosedCount;
                message.arg1 = choosedRow;
                message.arg2 = choosedColumn;
                handler.sendMessage(message);
                break;
        }
        return true;
    }


    /**
     * 获取该view的宽高
     */
    public void getWithAndHeight() {
        width = getWidth();
        height = getHeight();
        detalX = width/8;
        detalY = height/7;
    }

    public void setRowAndColumn(int row,int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * 设置已出售的座位
     * -1为出售的座位标志
     * @param seats
     */
    public void setSoldSeats(int[][] seats) {
        for (int i=0;i<seats.length; i++)
            for (int j=0; j<seats[0].length; j++) {
                if (seats[i][j] == -1)
                    this.seats[i][j] = -1;
            }
        invalidate();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
