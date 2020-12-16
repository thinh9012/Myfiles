package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageActivity extends AppCompatActivity {
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private File myFile;
    private String filePath = "ThinhFolder";
    private String fileName = "thinh.txt";
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        // init
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // event
        btnSaveClick();
    }

    private void init() throws IOException {
        // Request permission
        askPermissionAndWriteFile();
        askPermissionAndReadFile();

        this.btnSave = findViewById(R.id.btnSave);
    }

    // Xin cấp quyền ghi file
    private void askPermissionAndWriteFile() throws IOException {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(canWrite)
        {
            // File
//            ContextWrapper ctWraper = new ContextWrapper(getApplicationContext());
//            File dir = ctWraper.getDir(filePath, Context.MODE_PRIVATE);

            File dir2 = Environment.getDataDirectory();
            myFile = new File(dir2, fileName);
            myFile.createNewFile();
        }
    }

    // Xin cấp quyền đọc file
    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    // Với Android Level >= 23 bạn phải hỏi người dùng cho phép các quyền với thiết bị
    // (Chẳng hạn đọc/ghi dữ liệu vào thiết bị).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return true;
            }
        }
        return true;
    }

    private void btnSaveClick()
    {
        this.btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fos = new FileOutputStream(myFile);

                    fos.write("Thinh test content!!!".getBytes());
                    fos.close();
                    Toast.makeText(StorageActivity.this, "Success!!!", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}