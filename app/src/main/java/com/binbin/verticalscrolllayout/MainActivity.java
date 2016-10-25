package com.binbin.verticalscrolllayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
            ((Button)view.findViewById(R.id.bt)).setText("==="+i);
            if(i==0){
                view.setBackgroundColor(Color.RED);
                ((Button)view.findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"haha0",Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(i==1){
                view.setBackgroundColor(Color.GREEN);
                ((Button)view.findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"haha1",Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(i==2){
                view.setBackgroundColor(Color.BLUE);
                ((Button)view.findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"haha22",Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(i==3){
                view.setBackgroundColor(Color.CYAN);
                ((Button)view.findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"haha333",Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(i==4){
                view.setBackgroundColor(Color.YELLOW);
                ((Button)view.findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"haha444",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            mViews.add(view);
        }
        verticalScrollLayout.addViews(mViews);
    }
}
