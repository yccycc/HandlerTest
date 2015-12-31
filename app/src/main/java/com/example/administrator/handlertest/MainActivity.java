package com.example.administrator.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    TextView textView;
    Button button;
    Handler handler2;
    Handler handlerInOtherThread;
    Button button2;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.btn);
        button2 = (Button) findViewById(R.id.btn2);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, "handler1", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, "handler2", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();//look
                handlerInOtherThread = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Toast.makeText(MainActivity.this, "handlerInOtherThread", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                Looper.myLooper().loop();//look
            }
        }.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(++i + "");
                    }
                });
                handler.sendEmptyMessage(0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerInOtherThread.post(new Runnable() {
                    @Override
                    public void run() {
                       // textView.setText(++i + "");
                    }
                });
                handlerInOtherThread.sendEmptyMessage(0);
            }
        });
    }
}
