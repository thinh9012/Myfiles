package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.custom.CustomAdapter;
import com.example.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ListviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        this.getSupportActionBar().setTitle("CUSTOM LIST");

        List<Contact> datas = new ArrayList<Contact>();
        datas.add(new Contact("Việt Nam", "vn", "#ccc"));
        datas.add(new Contact("Mỹ", "us", "#555"));
        datas.add(new Contact("Nga", "ru", "#ccc"));

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        CustomAdapter customAdapter = new CustomAdapter(this, datas);

        ListView lstView = (ListView) findViewById(R.id.lstView);
        lstView.setAdapter(customAdapter);
    }
}