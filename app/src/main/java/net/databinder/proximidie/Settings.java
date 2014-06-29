package net.databinder.proximidie;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
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

import net.databinder.proximidie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            TriggerSettingsFragment settings = new TriggerSettingsFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, settings)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    pref.setKey("bluetooth_device_" + device.getAddress());
                    pref.setTitle(device.getName());
                    cat.addPreference(pref);
                }
            }
        }
    }
}
