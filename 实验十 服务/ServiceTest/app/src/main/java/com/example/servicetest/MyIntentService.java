package com.example.servicetest;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MyIntentService","Thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy executed");
    }
}
