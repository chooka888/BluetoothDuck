package com.example.chook.bttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.Set;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class DeviceList extends AppCompatActivity {

    //UI components
    Button btnPaired;
    ListView deviceList;

    //BT
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_list);

        btnPaired = (Button)findViewById(R.id.button2);
        deviceList = (ListView)findViewById(R.id.listView);

        //check the device hase BT
        myBluetooth =  BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {

            //if none display an error message
            Toast.makeText(getApplicationContext(), R.string.message_BT_Not_Available, Toast.LENGTH_LONG).show();

            //finish apk
            finish();

        }else if(!myBluetooth.isEnabled()){

            //ask the user to turn on the bluetooth
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);

        }

        btnPaired.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });

    }

    private void pairedDevicesList(){
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0){
            for (BluetoothDevice bt : pairedDevices){
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        }else{
            Toast.makeText(getApplicationContext(), "No paired Bluetooth devices found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(myListClickListener);

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3){

            //get the device mac address
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent i = new Intent(DeviceList.this, BTTerminal.class);

            //Change activity
            i.putExtra(EXTRA_ADDRESS, address); //This will be received by the next activity
            startActivity(i);
        }
    };

    private void requestDangerousPermissions(){

        //READ External Storage
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(DeviceList.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if(permissionCheck != PackageManager.PERMISSION_GRANTED){

            //If we don't have permission we better get some...
            ActivityCompat.requestPermissions(DeviceList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }

        //WRITE External Storage
        // Assume thisActivity is the current activity
        permissionCheck = ContextCompat.checkSelfPermission(DeviceList.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DeviceList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "permission Granted.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "The application will not work without graniting permission.", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
