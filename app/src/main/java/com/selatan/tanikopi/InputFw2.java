package com.selatan.tanikopi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Retrofit;

public class InputFw2 extends AppCompatActivity {
    private Date date;
    private TextView dateTime,jam,kodeProses,noProses;
    private EditText edNama,edJumlah;
    private String judul,sDate;
    private Button masuk,tambah;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_fw2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrofit = CallService.getClient();
        dateTime = (TextView) findViewById(R.id.txDate2);
        jam = (TextView) findViewById(R.id.txJam2);
        Typeface customFont =Typeface.createFromAsset(getAssets(),"font/DS-DIGI.TTF");
        jam.setTypeface(customFont);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = calendar.getTime();
        sDate = dateFormat.format(this.date);
        dateTime.setText(sDate);
        noProses = findViewById(R.id.txProsesKe);
        kodeProses = findViewById(R.id.txkdProses2);
        judul = getIntent().getStringExtra("kode_proses");
        kodeProses.setText(judul);
        getProsesKe();
//        judul= getIntent().getStringExtra("judul");
//        getSupportActionBar().setTitle("Proses "+judul);
        masuk = findViewById(R.id.btnInpProses2);
        tambah = findViewById(R.id.btnTambahProses);

        if (getIntent().getStringExtra("gabah").equals("kering")){
            tambah.setVisibility(View.INVISIBLE);
        }
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if((!edNama.getText().toString().isEmpty()) & (!edJumlah.getText().toString().isEmpty())) {
                    tambahProses();
                }else{
                    Toast.makeText(InputFw2.this,"Semua Data Harus Terisi", Toast.LENGTH_LONG).show();
                }
            }
        });
        edJumlah = findViewById(R.id.edJumlah2);
        edNama = findViewById(R.id.edNamaproses2);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!edNama.getText().toString().isEmpty()) & (!edJumlah.getText().toString().isEmpty())) {
                    addProses();
                }else{
                    Toast.makeText(InputFw2.this,"Semua Data Harus Terisi", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getProsesKe(){
        ProsesInput pi = new ProsesInput(judul,this,retrofit);
        pi.getProses_ke(noProses);

    }
    private void tambahProses(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String dateS = dateFormat.format(cal.getTime());
        final ProsesInput pi = new ProsesInput(dateS,kodeProses.getText().toString().trim(),noProses.getText().toString().substring(10),edJumlah.getText().toString().trim(),
                jam.getText().toString().trim()+":00",edNama.getText().toString().trim(),this,"no",retrofit);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Data dan Tambahkan Proses?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                pi.addDetailProses("n");
                getIntent().putExtra("kode_proses",judul);
                getIntent().putExtra("gabah","tidak");
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addProses(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String dateS = dateFormat.format(cal.getTime());
        final ProsesInput pi = new ProsesInput(dateS,kodeProses.getText().toString().trim(),noProses.getText().toString().substring(10),edJumlah.getText().toString().trim(),
                jam.getText().toString().trim()+":00",edNama.getText().toString().trim(),this,"yes",retrofit);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Data dan Akhiri Proses?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int id) {
                dialog.dismiss();
                AlertDialog.Builder build = new AlertDialog.Builder(InputFw2.this);
                build.setTitle("Simpan Sebagai Gabah Kering?");
                build.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pi.addDetailProses("y");
                Intent ea = new Intent(InputFw2.this,MainMenu.class);
                startActivity(ea);
                    }
                }); //curah hujan, tipe tanah misal tanah merang :)moisture konten?, prosessornya, gradenya, density, kejar ke SNI-nya, living incest :) grading sni dan sna :)) \\ frinsa
                build.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pi.addDetailProses("n");
                Intent ea = new Intent(InputFw2.this,MainMenu.class);
                startActivity(ea);
                    }
                });
                build.setCancelable(true);
                AlertDialog dialog2 = build.create();
                dialog2.show();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
