package zhipingok.com.lock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import static zhipingok.com.lock.LockView.OnCompeleteListener;


public class MainActivity extends ActionBarActivity implements OnCompeleteListener {

    private LockView lockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lockView = (LockView) findViewById(R.id.lock_view);
        lockView.setOnCompeleteListener(this);
    }



    @Override
    public void complete(int[] codes) {
        for(int i=0;i<codes.length;i++){
            Log.d("wzp",codes[i]+"");
        }
       // Log.d("wzp",)
    }
}
