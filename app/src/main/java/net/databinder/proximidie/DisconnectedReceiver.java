package net.databinder.proximidie;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DisconnectedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("receiver", "received something here: " + intent.getAction());
        if (intent.getAction().equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i("receiver", "device disconnected: " + device.getName() + " address:" +device.getAddress());
        }
    }
}
