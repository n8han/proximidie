package net.databinder.proximidie;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class DisconnectedReceiver extends BroadcastReceiver implements OnErrorListener {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Boolean shutdown = Settings.shutdownForDevice(context, device);
            Log.i("receiver", "device disconnected: " + device.getName() +
                  " address:" + device.getAddress() + " shutdown:" + shutdown);
            if (shutdown) {
                shutdown();
            }

        }
    }
    private void shutdown() {
        new ShutdownThread(this).start();
    }
    String TAG = DisconnectedReceiver.class.getName();

    public void onError(Exception exc) {
        Log.e(TAG, "Error on shutdown", exc);
    }

    @Override
    public void onError(String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void onNotRoot() {
        Log.e(TAG, "We don't have root priviliges");
    }
}
