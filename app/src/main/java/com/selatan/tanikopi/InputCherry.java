package com.selatan.tanikopi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputCherry extends AppCompatActivity {
    private Spinner spVar;
    private Button btnInputCherry;
    private EditText edKd,edId,edJml,edHrg, edTing;
    private RadioButton rbArbc, rbRbst;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_cherry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView dateTime = (TextView) findViewById(R.id.txDate);
        edTing = (EditText) findViewById(R.id.edTinggi);
        edTing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    int min = 1000;
                    int val = Integer.parseInt(edTing.getText().toString());
                    int max = 2000;
                    if (val<=min){
                        edTing.setText("1000");
                    }else if(val>=max){
                        edTing.setText("2000");
                    }
                }
            }
        });
        final EditText edNama = findViewById(R.id.edNamaPetani);

        spVar = (Spinner) findViewById(R.id.spVarient);
        btnInputCherry = (Button) findViewById(R.id.btnInputCherry);
        edHrg = (EditText) findViewById(R.id.edHarga);
        edJml = (EditText) findViewById(R.id.edJumlah);
        edId = (EditText) findViewById(R.id.edID_Petani);
        edId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    getNama(edId.getText().toString().trim(),edNama);
                }
            }
        });
        edKd = (EditText) findViewById(R.id.edKdtrx);
        rbArbc = (RadioButton) findViewById(R.id.rbArabica);
        rbRbst = (RadioButton) findViewById(R.id.rbRobusta);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = calendar.getTime();
        String date = dateFormat.format(this.date);
        dateTime.setText(date);
        btnInputCherry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!edKd.getText().toString().isEmpty()) & (!edId.getText().toString().isEmpty())
                        & (rbArbc.isChecked()==true||rbRbst.isChecked()==true)& (!edJml.getText().toString().isEmpty())& (!edHrg.getText().toString().isEmpty())
                        & (!edTing.getText().toString().isEmpty())& (!spVar.getSelectedItem().toString().isEmpty())) {
                    inputChery();
                }else{
                    Toast.makeText(InputCherry.this,"Semua Data Harus Terisi", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void inputChery(){
        String kodetrx = edKd.getText().toString().trim();
        String id_petani = edId.getText().toString().trim();
        String jenis = "";
        if (rbArbc.isChecked()==true) {
            jenis = "A";
        }else if (rbRbst.isChecked()==true){
            jenis = "R";
        }
        Double harga = Double.valueOf(edHrg.getText().toString().trim());
        Double jumlah = Double.valueOf(edJml.getText().toString().trim());
        Double ketinggian = Double.valueOf(edTing.getText().toString().trim());
        String varient = spVar.getSelectedItem().toString();
        Cherry ch = new Cherry(this,kodetrx,id_petani,jenis,date,jumlah,ketinggian,harga,varient);
        ch.addCherry();
    }
    private void getNama(String s, EditText edNama){
        Cherry ea = new Cherry(s,this);
        ea.setNamaPetani(edNama);
    }
}
