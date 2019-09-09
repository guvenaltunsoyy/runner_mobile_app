package com.example.runner_mobile_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UserService extends Service {
    public UserService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SERVICE","SERVICE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("START SERVICE","START SERVICE");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public boolean stopService(Intent name) {
        Log.i("stop","service stop");
        return true;
    }
}
