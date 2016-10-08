package com.binbin.verticalscrolllayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.binbin.verticalscrolllayout.VerticalScrollLayout;

public class MainActivity extends Activity {

    private VerticalScrollLayout verticalScrollLayout;
    private List<View> mViews=new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalScrollLayout= (VerticalScrollLayout) findViewById(R.id.activity_main);
        for (int i = 0; i < 5; i++) {
            View view=LayoutInflater.from(this).inflate(R.layout.view, null);
            ((TextView)view.findViewById(R.id.tv)).setText("---"+i);
            if(i==0){
                view.setBackgroundColor(Color.RED);
            }else if(i==1){
                view.setBackgroundColor(Color.GREEN);
            }else if(i==2){
                view.setBackgroundColor(Color.BLUE);
            }else if(i==3){
                view.setBackgroundColor(Color.CYAN);
            }else if(i==4){
                view.setBackgroundColor(Color.YELLOW);
            }
            mViews.add(view);
        }
        verticalScrollLayout.addViews(mViews);
    }
}
