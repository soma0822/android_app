package service;

import com.example.myapplication.IVirtualSensorService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

public class VirtualSensorService extends Service {
    private final IVirtualSensorService.Stub mBinder = new IVirtualSensorService.Stub() {
        private int mSensorValue = 10;

        @Override
        public int getSensorValue() {
            return mUptimeSeconds;
        }

        @Override
        public void setSensorValue(int value) {
            mSensorValue = value;
        }

    };

    private int mUptimeSeconds = 0;
    private Handler mHandler = new Handler();
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mUptimeSeconds++;
            mHandler.postDelayed(this, 1000); // 1秒ごとにカウントアップ
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        // サービスが作成されたときにタイマーを開始
        mHandler.postDelayed(mTimerRunnable, 1000); // 1秒後から開始
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // サービスが破棄されたときにタイマーを停止
        mHandler.removeCallbacks(mTimerRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Stubオブジェクトを返す
        return mBinder;
    }
}
