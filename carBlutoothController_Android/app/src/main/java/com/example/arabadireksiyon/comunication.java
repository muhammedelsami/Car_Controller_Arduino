package com.example.arabadireksiyon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.style.BackgroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import static com.example.arabadireksiyon.MainActivity.EXTRA_ADRESS;

public class comunication extends AppCompatActivity {

    String address =null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth= null;

    private StringBuilder recDataString = new StringBuilder();

    BluetoothSocket btSocket =null;
    BluetoothDevice remoteDevice;
    BluetoothServerSocket mmserver;

    private boolean isBtConnected = false;
    static  final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Button ileri, geri, sag, sol;
    Button sonraki, onfar,arkafar;
    SeekBar hiz;
    TextView deger;

    Button korna, stop,sagsinyal, solsinyal, full, kilit;

    Vibrator vibrator;

    boolean durum1 = false;
    boolean durum2 = false;
    boolean durum3 = false;
    boolean sag_sinyal_durum=false;
    boolean sol_sinyal_durum=false;
    boolean stop_durum=false;
    boolean full_durum=false;
    boolean kilit_durum=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunication);

        Intent newint = getIntent();
        address=newint.getStringExtra(EXTRA_ADRESS);

        ileri=(Button) findViewById(R.id.button_ileri);
        geri=(Button) findViewById(R.id.button_geri);
        sag=(Button) findViewById(R.id.button_sag);
        sol=(Button) findViewById(R.id.button_sol);

        onfar=(Button) findViewById(R.id.faron);
        arkafar=(Button) findViewById(R.id.fararka);

        korna=(Button) findViewById(R.id.korna);
        stop=(Button) findViewById(R.id.stop);
        sagsinyal=(Button) findViewById(R.id.sag_sinyal);
        solsinyal=(Button) findViewById(R.id.sol_sinyal);
        full=(Button) findViewById(R.id.ful_led);
        kilit=(Button) findViewById(R.id.kilit);



        hiz=(SeekBar) findViewById(R.id.seekBar3);
        deger=(TextView) findViewById(R.id.textView2);

        vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);





        korna.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (btSocket!=null){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            korna.setBackgroundDrawable(getResources().getDrawable(R.drawable.kk));
                            vibrator.vibrate(100);

                            try {
                                btSocket.getOutputStream().write("Z".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            korna.setBackgroundDrawable(getResources().getDrawable(R.drawable.kkkk));

                            try {
                                btSocket.getOutputStream().write("z".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;
                    }
                }
                return false;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);

                stop_durum=!stop_durum;
                if (stop_durum){
                    stop.setBackgroundDrawable(getResources().getDrawable(R.drawable.kk));

                    try {

                        btSocket.getOutputStream().write("H".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
                else {
                    stop.setBackgroundDrawable(getResources().getDrawable(R.drawable.kkkk));

                    try {
                        btSocket.getOutputStream().write("h".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
            }
        });
        sagsinyal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);
                sag_sinyal_durum=!sag_sinyal_durum;
                if (sag_sinyal_durum){
                    sagsinyal.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));

                    try {
                        btSocket.getOutputStream().write("U".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
                else {
                    sagsinyal.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));

                    try {
                        btSocket.getOutputStream().write("u".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }


            }
        });
        solsinyal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);

                sol_sinyal_durum=!sol_sinyal_durum;
                if (sol_sinyal_durum){
                    solsinyal.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));

                    try {
                        btSocket.getOutputStream().write("V".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
                else {
                    solsinyal.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));

                    try {
                        btSocket.getOutputStream().write("v".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
            }
        });
        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);

                full_durum=!full_durum;
                if (full_durum){
                    full.setBackgroundDrawable(getResources().getDrawable(R.drawable.kk));

                    try {
                        btSocket.getOutputStream().write("P".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
                else {
                    full.setBackgroundDrawable(getResources().getDrawable(R.drawable.kkkk));

                    try {
                        btSocket.getOutputStream().write("p".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
            }
        });
        kilit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);

                kilit_durum=!kilit_durum;
                if (kilit_durum){
                    kilit.setBackgroundDrawable(getResources().getDrawable(R.drawable.kk));

                    try {
                        btSocket.getOutputStream().write("K".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
                else {
                    kilit.setBackgroundDrawable(getResources().getDrawable(R.drawable.kkkk));

                    try {
                        btSocket.getOutputStream().write("k ".toString().getBytes());
                    }
                    catch (IOException e){

                    }
                }
            }
        });







        hiz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int a = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                a = i;
                vibrator.vibrate(100);
                if (btSocket != null) {
                    try {
                        btSocket.getOutputStream().write(a);
                    } catch (IOException e) {

                    }
                }
                deger.setText("Hiz Değeri : "+a);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        onfar.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                durum1=!durum1;
                if (durum1){
                    vibrator.vibrate(100);
                    onfar.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                    try {
                        btSocket.getOutputStream().write("W".toString().getBytes());
                    } catch (IOException e) {

                    }


                }
                else{

                    onfar.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));
                    try {
                        btSocket.getOutputStream().write("w".toString().getBytes());
                    } catch (IOException e) {

                    }

                }

            }
        });
        arkafar.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                durum2=!durum2;
                if (durum2){
                    vibrator.vibrate(100);
                    arkafar.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                    try {
                        btSocket.getOutputStream().write("E".toString().getBytes());
                    } catch (IOException e) {

                    }


                }
                else{

                    arkafar.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));
                    try {
                        btSocket.getOutputStream().write("e".toString().getBytes());
                    } catch (IOException e) {

                    }

                }

            }
        });


        ileri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (btSocket!=null){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            ileri.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                            vibrator.vibrate(100);

                            try {
                                btSocket.getOutputStream().write("F".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            ileri.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));

                            try {
                                btSocket.getOutputStream().write("S".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;
                    }
                }
                return false;
            }
        });


        geri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (btSocket!=null){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            geri.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                            vibrator.vibrate(100);

                            try {
                                btSocket.getOutputStream().write("B".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            geri.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));


                            try {
                                btSocket.getOutputStream().write("S".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;
                    }
                }
                return false;
            }
        });

        sag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (btSocket!=null){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            sag.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                            vibrator.vibrate(100);

                            try {
                                btSocket.getOutputStream().write("R".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            sag.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));

                            try {
                                btSocket.getOutputStream().write("S".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;
                    }
                }
                return false;
            }
        });

        sol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (btSocket!=null){
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            sol.setBackgroundDrawable(getResources().getDrawable(R.drawable.eee));
                            vibrator.vibrate(100);

                            try {
                                btSocket.getOutputStream().write("L".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            sol.setBackgroundDrawable(getResources().getDrawable(R.drawable.rrrrr));

                            try {
                                btSocket.getOutputStream().write("S".toString().getBytes());
                            }
                            catch (IOException e){

                            }
                            break;
                    }
                }
                return false;
            }
        });

        new BTbaglan().execute();
    }



    private void Disconnect(){
        if(btSocket!=null){
            try {
                btSocket.close();
            } catch (IOException e){
                // msg("Error");
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Disconnect();
    }


    class BTbaglan extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(comunication.this, "Baglanıyor...", "Lütfen Bekleyin");
        }


        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice cihaz = myBluetooth.getRemoteDevice(address);
                    btSocket = cihaz.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                // msg("Baglantı Hatası, Lütfen Tekrar Deneyin");
                Toast.makeText(getApplicationContext(),"Bağlantı Hatası Tekrar Deneyin",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //   msg("Baglantı Basarılı");
                Toast.makeText(getApplicationContext(),"Bağlantı Başarılı",Toast.LENGTH_SHORT).show();

                isBtConnected = true;
            }
            progress.dismiss();
        }

    }

}
