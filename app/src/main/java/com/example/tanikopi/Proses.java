package com.example.tanikopi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Proses extends AppCompatActivity {
    private TextView fw,h,n,welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        welcome = findViewById(R.id.txWelcome2);
        welcome.setText("Selamat Datang, "+Config.username);
        fw = findViewById(R.id.txProsFw);
        h = findViewById(R.id.txProsHon);
        n = findViewById(R.id.txProsNat);
        fw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                fullwash();
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                honey();
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                natural();
            }
        });
    }
    private void fullwash(){
        Intent i = new Intent(this,InputFw1.class);
        i.putExtra("judul","Fullwash");
        startActivity(i);
    }
    private void honey(){
        Intent i = new Intent(this,InputFw1.class);
        i.putExtra("judul","Honey");
        startActivity(i);
    }
    private void natural(){
        Intent i = new Intent(this,InputFw1.class);
        i.putExtra("judul","Natural");
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }
}
