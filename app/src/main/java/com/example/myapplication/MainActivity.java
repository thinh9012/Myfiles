package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyMessage";
    private ImageButton imageButton;
    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init component
        initComponent();

        // Components event
        imageButtonClick();
        btnTest1Click();
        btnTest2Click();
        btnTest3Click();
    }

    private void initComponent()
    {
        this.getSupportActionBar().setTitle("THINH TITLE");
        this.imageButton = findViewById(R.id.imageButton);
        this.btnTest1 = findViewById(R.id.btnTest1);
        this.btnTest2 = findViewById(R.id.btnTest2);
        this.btnTest3 = findViewById(R.id.btnTest3);
    }

    private void imageButtonClick()
    {
        this.imageButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent mainActivity2 = new Intent(MainActivity.this, MainActivity2.class);

                // Set param
                mainActivity2.putExtra("strParam1", "Thịnh value1");
                mainActivity2.putExtra("strParam2", "Thịnh value2");

                // Start main 2
                MainActivity.this.startActivity(mainActivity2);
            }
        });
    }

    private void btnTest1Click()
    {
        this.btnTest1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent recycleViewActivity = new Intent(MainActivity.this, RecycleviewActivity.class);
                MainActivity.this.startActivity(recycleViewActivity);
            }
        });
    }

    private void btnTest2Click()
    {
        this.btnTest2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent listViewActivity = new Intent(MainActivity.this, ListviewActivity.class);
                MainActivity.this.startActivity(listViewActivity);
            }
        });
    }

    private void btnTest3Click()
    {
        this.btnTest3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent storageActivity = new Intent(MainActivity.this, StorageActivity.class);
                MainActivity.this.startActivity(storageActivity);
            }
        });
    }





//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i(TAG, "onRestart");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i(TAG, "onResume");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy");
//    }
}