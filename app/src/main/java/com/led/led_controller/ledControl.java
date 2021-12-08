package com.led.led_controller;

import android.os.Bundle;

import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;


public class ledControl extends AppCompatActivity {

    Button btnOn, btnOff,btnOn2, btnOff2,btnOn3, btnOff3, btnDis,btnConso;
    SeekBar brightness;

    TextView lumn;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;

    BluetoothSocket btSocket = null;


    private boolean isBtConnected = false;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);


        setContentView(R.layout.activity_led_control);


        btnOn = (Button)findViewById(R.id.button2);
        btnOff = (Button)findViewById(R.id.button3);
        btnOn2 = (Button)findViewById(R.id.button11);
        btnOff2 = (Button)findViewById(R.id.button12);
        btnOn3 = (Button)findViewById(R.id.button9);
        btnOff3 = (Button)findViewById(R.id.button10);
        btnConso = (Button)findViewById(R.id.button5);

        btnDis = (Button)findViewById(R.id.button4);
        brightness = (SeekBar)findViewById(R.id.seekBar);


        lumn = (TextView)findViewById(R.id.lumn);

        btnConso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConsommation();
            }
        });


        new ConnectBT().execute();


        btnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed();      //method to turn on
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOffLed();   //method to turn off
            }
        });
        btnOn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed2();      //method to turn on
            }
        });

        btnOff2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOffLed2();   //method to turn off
            }
        });
        btnOn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed3();      //method to turn on
            }
        });

        btnOff3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOffLed3();   //method to turn off
            }
        });



        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser==true)
                {
                    lumn.setText(String.valueOf(progress));
                    try
                    {
                        btSocket.getOutputStream().write(String.valueOf(progress).getBytes());
                    }
                    catch (IOException e)
                    {

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }
    void openConsommation(){

        Intent intent =new Intent(this,consom.class);
        startActivity(intent);
    }
    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }

    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('A');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOffLed2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('C');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOffLed3()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('E');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('B');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOnLed2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('D');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void turnOnLed3()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write('F');
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
