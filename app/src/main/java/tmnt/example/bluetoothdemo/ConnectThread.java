package tmnt.example.bluetoothdemo;

import android.bluetooth.BluetoothSocket;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tmnt on 2017/2/20.
 */
public class ConnectThread implements Runnable {

    private final BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;
    private OutputStream outputStream;

    public ConnectThread(BluetoothSocket bluetoothSocket) throws IOException {
        mBluetoothSocket = bluetoothSocket;
        mInputStream = bluetoothSocket.getInputStream();

        outputStream = bluetoothSocket.getOutputStream();

    }

    @Override
    public void run() {

        byte[] bytes = new byte[1024];
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "bluetooth.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream1 = null;
        try {
            outputStream1 = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (mInputStream.read(bytes) != -1) {

                outputStream1.write(bytes, 0, bytes.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

}
