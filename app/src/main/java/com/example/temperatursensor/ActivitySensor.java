package com.example.temperatursensor;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySensor extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    TextView Suhu_text;
    String SQliteQuery;
    SensorManager sensorManager;
    float suhu;
    Sensor sensor;
    Button btn_dtbs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Suhu_text = findViewById(R.id.data);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensor == null){
            if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null ){
                Database();
                int MINUTES = 2;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        addData();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ActivitySensor.this, "Data telah masuk didalam Database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                }, 0,100*60*MINUTES);
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            }else {
                Suhu_text.setText("Sensor Termometer Tidak Tersedia");
            }
        }
        btn_dtbs = findViewById(R.id.btn_dtbs);
        btn_dtbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySensor.this, ActivityListView.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelListener);
    }
    SensorEventListener accelListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            suhu = sensorEvent.values[0];

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Suhu_text.setText((int)suhu + " °");
                    handler.postDelayed(this,2000);
                }
            },2000);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
    private void Database(){
        sqLiteDatabase = openOrCreateDatabase("Nama_Database_Baru", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Nama_Tabel (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR, suhu VARCHAR);");
    }
    private void addData(){
        SQliteQuery = "INSERT INTO Nama_Tabel (title,suhu) VALUES ('"+ getCurrentDate() +"', '"+ suhu +"');";
        sqLiteDatabase.execSQL(SQliteQuery);
    }
}
