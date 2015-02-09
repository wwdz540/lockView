package zhipingok.com.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cheshire_cat on 15/2/5.
 */
public class LockView extends View  {

    LockPoint[][] points=new LockPoint[3][3];
    LockLine line =new LockLine();

    public LockView(Context context) {
        super(context);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //this.
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        int stepWidth=(width-20)/2;
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                points[i][j]=new LockPoint(10,i*stepWidth+10,j*stepWidth+10);
                points[i][j].setId(i<<4|j);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint =new Paint();

        //让画出的图形是空心的
        paint.setStyle(Paint.Style.STROKE);
        //设置画出的线的 粗细程度
        paint.setStrokeWidth(5);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(50);

        //绘点
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                points[i][j].draw(canvas,paint);
            }
        }

        line.draw(canvas,paint);
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y= event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                line.clear();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                line.setM(x,y);
                break;
        }


        LockPoint mPoint;
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mPoint=points[i][j];
                if( mPoint.dis(x,y)<40){
                    if(!line.hasPoint(mPoint))
                        line.addPoint(mPoint);
                }
            }
        }
        invalidate();
       // Log.d("wzp","x:"+x+"  y:"+y);


        return true;
    }
}
