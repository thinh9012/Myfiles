package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.getSupportActionBar().hide();

        setValue();
        btnCloseExecute();
        eTxtMainExecute();
    }

    private void eTxtMainExecute() {
        final EditText eTxtMain = (EditText) findViewById(R.id.eTxtMain);
        eTxtMain.setOnEditorActionListener (new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ( keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String strETxtValue = eTxtMain.getText().toString();
                    if (strETxtValue == null || strETxtValue.isEmpty()){
                        Toast.makeText(MainActivity2.this, "Thinh test messagebox!!!", Toast.LENGTH_LONG).show();
                        return false;
                    }

                    try {
                        // Setting chip
                        ChipGroup cGroupMain =  (ChipGroup)findViewById(R.id.cGroupMain);
                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity2.this);
                        Chip newChip = (Chip) layoutInflater.inflate(R.layout.layout_chip_entry, cGroupMain, false);
                        newChip.setText(strETxtValue);

                        // Add new chip
                        cGroupMain.addView(newChip);

                        // Events
                        newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                Toast.makeText(MainActivity2.this, "Thinh test on change chip!!!", Toast.LENGTH_LONG).show();
                            }
                        });

                        newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Chip chip = (Chip) view;
                                ChipGroup parent = (ChipGroup) chip.getParent();
                                parent.removeView(chip);
                            }
                        });

                        eTxtMain.setText("");

                    }catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(MainActivity2.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
    }

    private void btnCloseExecute() {
        Button btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity2.this.finish(); // finish, return previous activity
            }
        });
    }

    private void setValue() {
        Intent mainActivityIntent = getIntent();

        String strParam1 = mainActivityIntent.getStringExtra("strParam1");
        String strParam2 = mainActivityIntent.getStringExtra("strParam2");

        TextView txtView1 = (TextView) findViewById(R.id.txtView1);
        TextView txtView2 = (TextView) findViewById(R.id.txtView2);

        txtView1.setText(strParam1);
        txtView2.setText(strParam2);
    }
}