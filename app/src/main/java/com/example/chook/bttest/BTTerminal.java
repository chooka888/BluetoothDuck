package com.example.chook.bttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

public class BTTerminal extends AppCompatActivity {

    Button btnClear;
    Button btnGo;
    Button btnDisConn;
    Button btnLoad;
    Button btnSave;
    TextView txtPayload;
    TextView txtName;

    private ProgressDialog progress;
    public BluetoothAdapter myBluetooth = null;
    public BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    String address;
    public Script myScript;
    public ExecutionEngine ee;

    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Receive payload from previous screen / triggering process
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //tell the layout to use the layout file
        setContentView(R.layout.activity_bt_terminal);

        //Get a refenece to the controls int he layout file
        btnClear = (Button)findViewById(R.id.btnClear);
        btnGo = (Button)findViewById(R.id.btnGo);
        btnDisConn = (Button)findViewById(R.id.btnDisConn);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnSave = (Button)findViewById(R.id.btnSave);
        txtPayload = (EditText)findViewById(R.id.txtPayload);
        txtName = (EditText)findViewById(R.id.txtName);

        //For testing put in script
        txtPayload.setSingleLine(false);

        //Create BT connection
        ConnectBT BTconn = new ConnectBT();
        BTconn.execute(); //Call the class to connect

        //Add Clear button event listener.
        btnClear.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Clear the textview...
                txtPayload.setText("");
                txtName.setText("");

            }
        });

        //Add Go Button listener
        btnGo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){

                try {

                    //Read the text into a Script Object
                    myScript = new Script(txtPayload.getText().toString());

                    //Execute the script
                    //TODO: work out why BTsocket was null when global ee was used.
                    ee = new ExecutionEngine(btSocket);
                    if (ee.executeScript(myScript)) {
                        msg("Script executed.");
                    }

                }catch (Exception ex){
                    //bubble any exceptions up.
                    msg(ex.getMessage());
                }

            }
        });

        btnDisConn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Disconnect();
            }
        });

        //add load on click listener
        btnLoad.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FileChooser fc = new FileChooser(BTTerminal.this,Environment.getExternalStoragePublicDirectory("BlueFang"));
                    fc.setExtension("txt");

                    fc.setFileListener(new FileChooser.FileSelectedListener() {
                        @Override public void fileSelected(final File file) {
                            try{
                                // do something with the file
                                String fileContent = "";
                                FileInputStream fileInputStream = new FileInputStream(file);
                                BufferedReader myReader = new BufferedReader(new InputStreamReader(fileInputStream));
                                String s;

                                while ((s = myReader.readLine())!= null){
                                    fileContent += s + System.getProperty("line.separator");
                                }
                                myReader.close();
                                txtPayload.setText(fileContent);
                                txtName.setText(file.getName());

                            } catch (Exception ex) {
                                msg(ex.getMessage());
                            }

                        }});


                    fc.showDialog();

                } catch (Exception ex) {
                    msg(ex.getMessage());
                }
            }
        });


        //add Save Button Listener
        btnSave.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    //If permission save it...
                    String fileName = txtName.getText().toString();
                    if (fileName != null && fileName != "") {
                        saveFile(fileName);
                    }else{
                        msg("you must add a file name before saving.");
                    }

                } catch (Exception ex) {
                    msg(ex.getMessage());
                }
            }

        });

        //Show or hide buttons if external storage is not available.
        btnSave.setEnabled(isExternalStorageWritable());
        btnLoad.setEnabled(isExternalStorageReadable());

    }

    private void saveFile(String filename) throws IOException {

        //check if external storage is mounted and accessable.
        if(isExternalStorageWritable()){

            //get external path for this app
            File path = Environment.getExternalStoragePublicDirectory("BlueFang");

            //Create the directory if it doesn't exist
            path.mkdir();

            //Create the file
            File file = new File(path, filename);

            //file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(txtPayload.getText());
            myOutWriter.close();
            fOut.close();

            msg(file.getAbsolutePath().toString());

        }

    }

    private void loadFile(String fileName) throws IOException {
        if(isExternalStorageReadable()){
            String fileContent = "";
            //get external path for this app
            File path = Environment.getExternalStoragePublicDirectory("BlueFang");
            File file = new File(path, fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String s;

            while ((s = myReader.readLine())!= null){
                fileContent += s + System.getProperty("line.separator");
            }
            myReader.close();
            txtPayload.setText(fileContent);

        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    private ArrayList<Command> readInCommandList(){
        ArrayList<Command> cmdList = new ArrayList<Command>();

        String mutliLines = txtPayload.getText().toString();
        String[] lines;
        String delimiter = "\n";
        lines = mutliLines.split(delimiter);

        for(String s: lines){
            cmdList.add(new Command(s));
        }

        return cmdList;
    }

    private void readText(){

        String mutliLines = txtPayload.getText().toString();
        String[] lines;
        String delimiter = "\n";
        lines = mutliLines.split(delimiter);

        for(String s: lines){
            sendText(s);
            sendText("\n");
        }

    }

    //Method to send content
    private void sendText(String s){
        if(btSocket != null){
            try {
                btSocket.getOutputStream().write(s.getBytes());
            }catch (IOException e){
                msg(e.getMessage());
            }
        }
    }

    //method to send raw codes
    private void sendCode(int a){
        if(btSocket != null){
            try {
                btSocket.getOutputStream().write(a);
            }catch (IOException e){
                msg(e.getMessage());
            }
        }
    }

    //Method to disconnect
    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    //Private Class to manage BT connections
    private class ConnectBT extends AsyncTask<Void, Void, Void> //UI Thread
    {
        //private variable
        private boolean ConnectionSuccess = true;

        @Override
        protected void onPreExecute(){
            progress = ProgressDialog.show(BTTerminal.this, "Connecting...", "Please wait.");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                if (btSocket == null || !isBtConnected){
                    myBluetooth = BluetoothAdapter.getDefaultAdapter(); //get the mobiles bluetooth
                    BluetoothDevice btd = myBluetooth.getRemoteDevice(address);
                    btSocket = btd.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }catch(IOException e){
                ConnectionSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void results) //after the doInBackground it checks if everything went fine
        {
            super.onPostExecute(results);

            if(!ConnectionSuccess){
                msg("Connection Failed. Try again.");
            }else{
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
