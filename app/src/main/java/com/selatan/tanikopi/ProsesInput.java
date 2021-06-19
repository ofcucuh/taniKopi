package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProsesInput {
    private String kd_proses,jenis,kd_transaksi,proses_ke,tgl,jumlah,jam_balik,nama_proses,last;
    private Context context;
    private Retrofit retrofit;
    private ApiService apiEndpointService;

    public ProsesInput(String kd_proses, String jenis, String kd_transaksi, String proses_ke, String tgl, String jumlah, String jam_balik, String nama_proses, Context context, Retrofit retrofit) {
        this.kd_proses = kd_proses;
        this.jenis = jenis;
        this.kd_transaksi = kd_transaksi;
        this.proses_ke = proses_ke;
        this.tgl = tgl;
        this.jumlah = jumlah;
        this.jam_balik = jam_balik;
        this.nama_proses = nama_proses;
        this.context = context;
        this.retrofit = retrofit;
        apiEndpointService = retrofit.create(ApiService.class);
    }
    public ProsesInput(String tgl, String kd_proses,String proses_ke, String jumlah,
                       String jam_balik, String nama_proses, Context context,String last,Retrofit retrofit) {
        this.tgl = tgl;
        this.kd_proses = kd_proses;
        this.proses_ke = proses_ke;
        this.jumlah = jumlah;
        this.jam_balik = jam_balik;
        this.nama_proses = nama_proses;
        this.context = context;
        this.last = last;
        this.retrofit = retrofit;
        apiEndpointService = retrofit.create(ApiService.class);
    }
    public ProsesInput(String kd_proses, Context context, Retrofit retrofit){
        this.kd_proses=kd_proses;
        this.context = context;
        this.retrofit = retrofit;
        apiEndpointService = retrofit.create(ApiService.class);
    }
    public void setProses(String s){
        this.proses_ke = s;
    }
    public void getProses_ke(final TextView tx){
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Mengambil Data...", "Mohon Tunggu...", false, false);
        Call<String> call = apiEndpointService.getProsesKe(kd_proses);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tx.setText("Proses Ke " + String.valueOf(Integer.parseInt(response.body().toString()) + 1));
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,t.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });
    }

    public void addProses(){
        ProgressDialog loading;
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);
        Call<String> call = apiEndpointService.addProses(kd_proses,
                jenis,kd_transaksi,proses_ke,
                jam_balik,tgl,jumlah,nama_proses);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("Berhasil Input")) {
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(context, InputFw2.class);
                    i.putExtra("kode_proses", kd_proses);
                    i.putExtra("gabah", "belum");
                    context.startActivity(i);
                }else{
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });
    }

    public void addDetailProses(final String kk) {
        ProgressDialog loading;
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);
        Call<String> call = apiEndpointService.addDetailProses(kd_proses,last,kk,proses_ke,jam_balik,tgl,jumlah,nama_proses);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loading.dismiss();
                Toast.makeText(context,response.body().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public String toString() {
        return "ProsesInput{" +
                "kd_proses='" + kd_proses + '\'' +
                ", jenis='" + jenis + '\'' +
                ", kd_transaksi='" + kd_transaksi + '\'' +
                ", proses_ke='" + proses_ke + '\'' +
                ", tgl='" + tgl + '\'' +
                ", jumlah='" + jumlah + '\'' +
                ", jam_balik='" + jam_balik + '\'' +
                ", nama_proses='" + nama_proses + '\'' +
                ", last='" + last + '\'' +
                '}';
    }

}
