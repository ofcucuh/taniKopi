package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InputFw1 extends AppCompatActivity {
    private Date date;
    private TextView dateTime,jam,kodeProses;
    private EditText edNama,edJumlah,edTrx;
    private String judul,sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_fw1);
        kodeProses = findViewById(R.id.txkdProses);
        id();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dateTime = (TextView) findViewById(R.id.txDate);
        jam = (TextView) findViewById(R.id.txJam);
        Typeface customFont =Typeface.createFromAsset(getAssets(),"font/DS-DIGI.TTF");
        jam.setTypeface(customFont);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = calendar.getTime();
        sDate = dateFormat.format(this.date);
        dateTime.setText(sDate);
        judul= getIntent().getStringExtra("judul");
        getSupportActionBar().setTitle("Proses "+judul);
        Button masuk = findViewById(R.id.btnInpProses);
        edTrx = findViewById(R.id.edKd_tranx);
        edJumlah = findViewById(R.id.edJumlah);
        edNama = findViewById(R.id.edNamaProses);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!edNama.getText().toString().isEmpty()) & (!edJumlah.getText().toString().isEmpty()) & (!edTrx.getText().toString().isEmpty())) {
                    addProses(kodeProses.getText().toString().trim());
                }else{
                    Toast.makeText(InputFw1.this,"Semua Data Harus Terisi", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
    private void addProses(String s){
        String kd_pro, jenis, kd_trx;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String dateS = dateFormat.format(cal.getTime());
        kd_pro = s;
        kd_trx = edTrx.getText().toString().trim();
        if (judul.equals("Fullwash")){
            jenis = "fw";
            ProsesInput pi = new ProsesInput(kd_pro,jenis,kd_trx,
                    "1",dateS,edJumlah.getText().toString().trim(),
                    jam.getText().toString().trim()+":00",edNama.getText().toString().trim(),this);
            pi.addProses();
//            System.out.println(pi.toString());
        }else if(judul.equals("Natural")){
            jenis = "n";
            ProsesInput pi = new ProsesInput(kd_pro,jenis,kd_trx,
                    "1",dateS,edJumlah.getText().toString().trim(),
                    jam.getText().toString().trim()+":00",edNama.getText().toString().trim(),this);
            pi.addProses();
        }else{
            jenis = "h";
            ProsesInput pi = new ProsesInput(kd_pro,jenis,kd_trx,
                    "1",dateS,edJumlah.getText().toString().trim(),
                    jam.getText().toString().trim()+":00",edNama.getText().toString().trim(),this);
            pi.addProses();
        }
//        Intent i = new Intent(this, Stok.class);
//        startActivity(i);
    }
    private void id(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(this, "Mengambil ID...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);

        AndroidNetworking.post(Config.URL_GET_ID)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        String ss;
                        loading.dismiss();
                        if (!s.equals("")) {
                            ss = String.valueOf(Integer.parseInt(s) + 1);
                            if (judul.equals("Fullwash")) {
                                kodeProses.setText("FW/" + ss + "/" + sDate);
                            } else if (judul.equals("Natural")) {
                                kodeProses.setText("N/" + ss + "/" + sDate);
                            } else {
                                kodeProses.setText("H/" + ss + "/" + sDate);
                            }
                        } else {
                            if (judul.equals("Fullwash")) {
                                kodeProses.setText("FW/" + 0 + "/" + sDate);
                            } else if (judul.equals("Natural")) {
                                kodeProses.setText("N/" + 0 + "/" + sDate);
                            } else {
                                kodeProses.setText("H/" + 0 + "/" + sDate);
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        id();
                    }
                });
    }

}
