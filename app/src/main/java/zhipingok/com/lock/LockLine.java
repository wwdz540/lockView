package zhipingok.com.lock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cheshire_cat on 15/2/9.
 */
public class LockLine {
   private List<LockPoint> list=new ArrayList<>();

   private float mx;
   private  float my;

   public void addPoint(LockPoint point){

       list.add(point);
    }

    public void clear(){
        mx=0;
        my=0;
        for(int i=0;i<list.size();i++){
            list.get(i).setState(0);
        }
        list.clear();
    }

    public void setM(float mx,float my){
        this.mx=mx;
        this.my=my;
    }

    public boolean hasPoint(LockPoint point){
        for(int i=list.size()-1;i>=0;i--){

            if(list.get(i).getId()==point.getId())return true;
        }
        return  false;
    }

    public List<LockPoint> getPoints(){
        return list;
    }


    public void draw(Canvas canvas,Paint paint){
        int pointSize=list.size();
        if(pointSize == 0) return;

        paint.setColor(Color.CYAN);
        LockPoint starPoint=list.get(0)
                ,endPoint;
        for(int i=1;i<pointSize;i++){
            endPoint=list.get(i);
            canvas.drawLine(starPoint.getX(),starPoint.getY(),endPoint.getX(),endPoint.getY(),paint);
            starPoint=endPoint;
        }
        if(!(mx==0&&my==0))
         canvas.drawLine(starPoint.getX(),starPoint.getY(),mx,my,paint);
    }
}
