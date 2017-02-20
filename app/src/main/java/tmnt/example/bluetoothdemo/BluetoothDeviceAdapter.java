package tmnt.example.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tmnt on 2017/2/20.
 */
public class BluetoothDeviceAdapter extends BaseAdapter {

    private List<BluetoothDevice> mDeviceList;
    private Context mContext;

    public BluetoothDeviceAdapter(List<BluetoothDevice> deviceList, Context context) {
        mDeviceList = deviceList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_lay, parent, false);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView address = (TextView) view.findViewById(R.id.address);

        name.setText(mDeviceList.get(position).getName());
        address.setText(mDeviceList.get(position).getAddress());
        return view;
    }
}
