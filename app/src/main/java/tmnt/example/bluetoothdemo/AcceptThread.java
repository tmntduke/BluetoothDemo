package tmnt.example.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by tmnt on 2017/2/20.
 */
public class AcceptThread implements Runnable {

    public static final UUID MY_UUID = UUID.randomUUID();

    private final BluetoothServerSocket mBluetoothServerSocket;

    public AcceptThread(BluetoothAdapter adapter) throws IOException {
        mBluetoothServerSocket = adapter.listenUsingRfcommWithServiceRecord("tmnt", MY_UUID);
    }


    @Override
    public void run() {

        BluetoothSocket socket = null;
        while (true) {
            try {
                socket = mBluetoothServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void cancle() throws IOException {
        if (mBluetoothServerSocket != null) {
            mBluetoothServerSocket.close();
        }
    }
}
