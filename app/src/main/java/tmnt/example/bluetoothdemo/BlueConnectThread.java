package tmnt.example.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by tmnt on 2017/2/20.
 */
public class BlueConnectThread implements Runnable {

    public final BluetoothSocket mBluetoothSocket;
    private final BluetoothDevice mBluetoothDevice;
    private final BluetoothAdapter mBluetoothAdapter;

    public BlueConnectThread(BluetoothDevice bluetoothDevice, BluetoothAdapter adapter) throws IOException {
        mBluetoothDevice = bluetoothDevice;
        mBluetoothAdapter = adapter;
        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(AcceptThread.MY_UUID);
    }

    @Override
    public void run() {
        mBluetoothAdapter.cancelDiscovery();

        try {
            mBluetoothSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
