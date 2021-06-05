package com.selatan.tanikopi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    TextView cherry,proses,stok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        proses = findViewById(R.id.txMenuProses);
        cherry = findViewById(R.id.txMenuCherry);
        stok = findViewById(R.id.txMenuStok);
        TextView welcome = findViewById(R.id.txUser);
        welcome.setText("Selamat Datang, "+Config.username);
        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                proses();
            }
        });
        cherry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                cherry();
            }
        });
        stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start Proses Fullwash
                stok();
            }
        });
        if(Config.username==null){
            Toast.makeText(this,"Sesi Anda Sudah Habis, Silahkan Login Kembali",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
    @Override
    public void onBackPressed(){
        final Intent isn=new Intent(this,MainActivity.class);
        new AlertDialog.Builder(this).setTitle("Anda Yakin?")
                .setMessage("Apakah anda yakin untuk keluar?")
                .setNegativeButton("Tidak",null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(isn);
                    }
                }).create().show();
    }
    private void proses(){
        Intent i = new Intent(this,Proses.class);
        startActivity(i);
    }
    private void cherry(){
        Intent i = new Intent(this,InputCherry.class);
        startActivity(i);
    }
    private void stok(){
        Intent i = new Intent(this,Stok.class);
        startActivity(i);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }
}
