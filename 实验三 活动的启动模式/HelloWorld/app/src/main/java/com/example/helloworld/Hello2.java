package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Hello2 extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Hello2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello2);
        Log.d("Hello2","Task id is" + getTaskId());
        Log.d(TAG, "onCreate execute");
        setTitle("Hello2");
        settupButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Hello2", "onDestroy");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btToHello1) {
            Intent intent;
            intent = new Intent(this, Hello1.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btToHello2) {
            Intent intent;
            intent = new Intent(this, Hello2.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btToHello3) {
            Intent intent;
            intent = new Intent(this, Hello3.class);
            startActivity(intent);
        }
    }

    private void settupButtons() {
        Button b;

        b = (Button) findViewById(R.id.btToHello1);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.btToHello2);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Hello2.this,Hello3.class);
                startActivity(intent);
            }
        });
        b = (Button) findViewById(R.id.btToHello3);
        b.setOnClickListener(this);
    }
}
