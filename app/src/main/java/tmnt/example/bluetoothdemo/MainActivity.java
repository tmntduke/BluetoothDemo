package tmnt.example.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<BluetoothDevice> mList;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDeviceAdapter adapter;
    private static final int REQUEST_BLUETOOTH = 1001;
    BlueConnectThread thread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        mList = new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(MainActivity.this, "this device don`t support bluetooth", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, REQUEST_BLUETOOTH);
        }

        adapter = new BluetoothDeviceAdapter(mList, this);
        mListView.setAdapter(adapter);

        bluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(new BluetoothRecevier(), filter);


        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivityForResult(intent, 1002);

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            mList.add(device);
            adapter.notifyDataSetChanged();
        }

        try {
            new Thread(new AcceptThread(bluetoothAdapter)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    thread = new BlueConnectThread(mList.get(position), bluetoothAdapter);

                    new Thread(thread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    class BluetoothRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mList.add(device);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(MainActivity.this, "bluetooth don`t enable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
