package com.example.arabadireksiyon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter myBluetooth;
    private Set<BluetoothDevice> pairedDevices;

    Button toggle_button, pair_button;
    ListView pairedlist;

    public static String EXTRA_ADRESS= "device_address";
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBluetooth=BluetoothAdapter.getDefaultAdapter();
        toggle_button=(Button) findViewById(R.id.button2);
        pair_button=(Button) findViewById(R.id.goster);
        pairedlist=(ListView) findViewById(R.id.listem);
        toggle_button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                toggleBluetooth();
            }


        });
        pair_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listdevice();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.item1){
            Intent hakkimda = new Intent(MainActivity.this, hakkimda.class);
            startActivity(hakkimda);
        }

        return true;
    }

    private void listdevice() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0){
            for (BluetoothDevice bt: pairedDevices){
                list.add(bt.getName()+"/n"+bt.getAddress());
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"cihaz yok.", Toast.LENGTH_SHORT).show();

        }
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        pairedlist.setAdapter(adapter);
        pairedlist.setOnItemClickListener(selectDevice);

    }

    private void toggleBluetooth() {

        if(myBluetooth==null){
            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        }
        if(!myBluetooth.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
        }
        if(myBluetooth.isEnabled()){
            myBluetooth.disable();
        }
    }

    public AdapterView.OnItemClickListener selectDevice = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length()-17);

            Intent comintent = new Intent(MainActivity.this, comunication.class);
            comintent.putExtra(EXTRA_ADRESS, address);
            startActivity(comintent);


        }
    };


}