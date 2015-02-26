package zhipingok.com.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by cheshire_cat on 15/2/5.
 */
public class LockView extends View  {

    LockPoint[][] points=new LockPoint[3][3];
    LockLine line =new LockLine();
    private OnCompeleteListener  compeleteListener=null;

    private float feelDis ,padding;
    private int preX=-1,preY=-1;


    public LockView(Context context) {
        super(context);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {

       super(context, attrs, defStyleAttr);
       feelDis = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,context.getResources().getDisplayMetrics());
       padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,context.getResources().getDisplayMetrics());
    }

    public void setOnCompeleteListener(OnCompeleteListener listener){
        this.compeleteListener=listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());
        int stepWidth=(width-(int)padding*2)/2;
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                points[i][j]=new LockPoint(10,i*stepWidth+padding,j*stepWidth+padding);
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
                line.setM(0,0);

                if(compeleteListener!=null){
                    List<LockPoint> pts = line.getPoints();
                    int ptsSzie=pts.size();
                    int[] ids=new int[ptsSzie];
                    for(int i=0;i<ptsSzie;i++){
                        ids[i]=pts.get(i).getId();
                    }

                    compeleteListener.complete(ids);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                line.setM(x, y);
                break;
        }

        addPoint(x,y);
        invalidate();
        return true;
    }

    private void addPoint(float x,float y){
        LockPoint mPoint;
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mPoint=points[i][j];
                if( mPoint.dis(x,y)<feelDis){
                    if(!line.hasPoint(mPoint)) {
                        if(preX!=-1 && preY!=-1){
                            addOnLinePoint(preX,preY,i,j);
                        }
                        preX=i;
                        preY=j;
                        mPoint.setState(1);
                        line.addPoint(mPoint);
                    }
                }
            }
        }
    }

    //将同一线的点加入线中
   private void addOnLinePoint(int startX,int startY,int endX,int endY){

       float dx=startX-endX,
               dy=startY-endY;


       float n=(dy==0)?-1:dx/(dy);
       float jj=startX-n*startY;


       Log.d("ex:","x="+n+"y+"+jj);
           int mStartX=Math.min(startX,endX),
                mStartY=Math.min(startY,endY),
                mEndX=Math.max(startX,endX),
                mEndY=Math.max(startY,endY);

       Log.d("yy","startY="+startY+", endY="+endY);
       LockPoint mPoint;
       for(int y=mStartY;y<=mEndY;y++){


            float x=n*y+jj;
           Log.d("yy","x="+x);
            if((x >= mStartX && x<=mEndX)||n==-1){
                mPoint=points[(int)x][y];
                mPoint.setState(1);
                line.addPoint(mPoint);
            }
       }
    }


    public static interface OnCompeleteListener{
        void complete(int[] codes);
    }
}
