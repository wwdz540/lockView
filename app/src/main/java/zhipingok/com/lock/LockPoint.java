package zhipingok.com.lock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by cheshire_cat on 15/2/9.
 */
public class LockPoint {

    private float radio;
    private float x;
    private float y;

    private int id;

    private int state=0;

    public LockPoint(float radio, float x, float y) {
        this.radio = radio;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }


    public int getId() { return id;  }

    public void setId(int id) { this.id = id;  }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void draw(Canvas canvas, Paint paint) {
       int color = Color.GRAY;
       switch (state){
           case 1:
               color=Color.GREEN;
       }
        paint.setColor(color);
        canvas.drawCircle(x, y, radio, paint);
    }

    public double dis(float sx, float sy) {
        return Math.sqrt((sx - this.x) * (sx - this.x) + (sy - this.y) * (sy - this.y));
    }
}
