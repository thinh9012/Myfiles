package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.custom.PeopleAdapter;
import com.example.model.People;

import java.util.ArrayList;
import java.util.List;

public class RecycleviewActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);

        // Create data
        List<People> lstPeople = createPeople();

        // Set data adapter
        PeopleAdapter peopleAdapter = new PeopleAdapter(this, lstPeople);
        recyclerViewMain = findViewById(R.id.recycleViewMain);
        recyclerViewMain.setAdapter(peopleAdapter);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMain.setItemAnimator(new DefaultItemAnimator());
        peopleAdapter.getItemId(2);
//        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private List<People> createPeople(){
        List<People> lstResult = new ArrayList<People>();
        lstResult.add(new People("avatar1", "Name 1", 18, 0xFFCCCCCC));
        lstResult.add(new People("avatar2", "Name 2", 19, 0xFF555555));
        lstResult.add(new People("avatar3", "Name 3", 20, 0xFFCCCCCC));

        return lstResult;
    }
}