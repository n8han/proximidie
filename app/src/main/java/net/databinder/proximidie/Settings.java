package net.databinder.proximidie;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

import net.databinder.proximidie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Settings extends ActionBarActivity {

    private static String MAIN_SWITCH = "pref_key_main_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TriggerSettingsFragment settings = new TriggerSettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, settings)
                .commit();


    }

    public static class TriggerSettingsFragment extends PreferenceFragment {

        public TriggerSettingsFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            addBluetoothDevices((PreferenceCategory) findPreference("pref_key_devices_cat"));
        }

        private void addBluetoothDevices(PreferenceCategory cat) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Log.e(Settings.class.getName(), "Unable to get bluetooth adapter");
            } else {
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                for (BluetoothDevice device : pairedDevices) {
                    CheckBoxPreference pref = new CheckBoxPreference(getActivity().getApplicationContext());
                    pref.setKey(devicePreferenceKey(device));
                    pref.setTitle(device.getName());
                    cat.addPreference(pref);
                    pref.setDependency(MAIN_SWITCH);
                }
            }
        }
    }
    private static String devicePreferenceKey(BluetoothDevice device) {
        return "bluetooth_device_" + device.getAddress();
    }

    public static Boolean shutdownForDevice(Context context, BluetoothDevice device) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean main = prefs.getBoolean(MAIN_SWITCH, false);
        return main && prefs.getBoolean(Settings.devicePreferenceKey(device), false);
    }
}
