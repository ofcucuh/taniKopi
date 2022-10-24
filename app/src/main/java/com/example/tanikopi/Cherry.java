package com.example.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Cherry {
    private Context context;
    private String kd_transaksi,id_petani,jenis,varietas;
    private Date tgl;
    private Double jumlah,ketinggian,harga;
    private Retrofit retrofit;
    private ApiService apiEndpointService;

    public Cherry(Context context, String kd_transaksi, String id_petani, String jenis, Date tgl, Double jumlah, Double ketinggian, Double harga, String varietas, Retrofit retrofit) {
        this.context = context;
        this.kd_transaksi = kd_transaksi;
        this.id_petani = id_petani;
        this.jenis = jenis;
        this.tgl = tgl;
        this.jumlah = jumlah;
        this.ketinggian = ketinggian;
        this.harga = harga;
        this.varietas = varietas;
        this.retrofit = retrofit;
        apiEndpointService = retrofit.create(ApiService.class);
    }
    public Cherry(String id_petani,Context context, Retrofit retrofit){
        this.context=context;
        this.id_petani=id_petani;
        this.retrofit = retrofit;
        apiEndpointService = retrofit.create(ApiService.class);
    }
    public void addCherry(){
        ProgressDialog loading;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(tgl);
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, true);


        Call<String> call = apiEndpointService.addCherry(kd_transaksi,
                id_petani,
                String.valueOf(harga),
                jenis,String.valueOf(jumlah),date,
                String.valueOf(ketinggian),varietas);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    public void setNamaPetani(EditText tx){
        Call<String> call = apiEndpointService.getNamaPetani(id_petani);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.body().toString().isEmpty() || response.body()!=null){
                    tx.setText(response.body().toString());
                }else
                    tx.setText("Nama Petani Tidak Ditemukan!");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
