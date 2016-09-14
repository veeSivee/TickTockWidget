package com.tes.vi.myticktockwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bcgdv.asia.lib.ticktock.TickTockView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TickTockView mCountDown = null;
    private TickTockView mCountUp = null;
    private TextView mTxtHeadline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        mCountDown = (TickTockView)findViewById(R.id.view_ticktock_countdown);
        mCountUp = (TickTockView)findViewById(R.id.view_ticktock_countup);
        mTxtHeadline = (TextView)findViewById(R.id.tvHello);

        if(mCountDown!=null){
            mCountDown.setOnTickListener(new TickTockView.OnTickListener() {
                @Override
                public String getText(long timeRemainingInMillis) {
                    int seconds = (int) (timeRemainingInMillis / 1000) % 60;
                    int minutes = (int) ((timeRemainingInMillis / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemainingInMillis / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemainingInMillis / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;
                    return String.format("%1$02d%4$s %2$02d%5$s %3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");
                }
            });
        }

        if(mCountUp!=null){
            mCountUp.setOnTickListener(new TickTockView.OnTickListener() {

                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                Date date = new Date();

                @Override
                public String getText(long timeRemainingInMillis) {

                    date.setTime(System.currentTimeMillis());
                    return format.format(date);
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 1);
        end.add(Calendar.SECOND, 5);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        if (mCountDown != null) {
            mCountDown.start(start, end);
        }

        Calendar c2= Calendar.getInstance();
        c2.add(Calendar.HOUR, 1);
        c2.set(Calendar.MINUTE, 5);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        if (mCountUp != null) {
            mCountUp.start(c2);
        }
    }

    @Override
    protected void onStop() {
        mCountDown.stop();
        mCountUp.stop();
        super.onStop();
    }
}
