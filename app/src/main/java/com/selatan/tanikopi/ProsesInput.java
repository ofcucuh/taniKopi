package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;


public class ProsesInput {
    private String kd_proses,jenis,kd_transaksi,proses_ke,tgl,jumlah,jam_balik,nama_proses,last;
    private Context context;

    public ProsesInput(String kd_proses, String jenis, String kd_transaksi, String proses_ke, String tgl, String jumlah, String jam_balik, String nama_proses, Context context) {
        this.kd_proses = kd_proses;
        this.jenis = jenis;
        this.kd_transaksi = kd_transaksi;
        this.proses_ke = proses_ke;
        this.tgl = tgl;
        this.jumlah = jumlah;
        this.jam_balik = jam_balik;
        this.nama_proses = nama_proses;
        this.context = context;
    }
    public ProsesInput(String tgl, String kd_proses,String proses_ke, String jumlah,
                       String jam_balik, String nama_proses, Context context,String last) {
        this.tgl = tgl;
        this.kd_proses = kd_proses;
        this.proses_ke = proses_ke;
        this.jumlah = jumlah;
        this.jam_balik = jam_balik;
        this.nama_proses = nama_proses;
        this.context = context;
        this.last = last;
    }
    public ProsesInput(String kd_proses, Context context){
        this.kd_proses=kd_proses;
        this.context = context;
    }
    public void setProses(String s){
        this.proses_ke = s;
    }
    public void getProses_ke(final TextView tx){
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Mengambil Data...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);
        AndroidNetworking.post(Config.URL_GET_PROSES_KE)
                .addBodyParameter("kd_proses",kd_proses)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        String ea;
                        if (s.equals("")) {
                            Toast.makeText(context, "Terjadi Kesalahan, Silahkan Coba Lagi!", Toast.LENGTH_LONG).show();
                        } else {
                            ea = String.valueOf(Integer.parseInt(s) + 1);
                            tx.setText("Proses Ke " + ea);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        getProses_ke(tx);
                    }
                });
    }

    public void addProses(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);

        AndroidNetworking.post(Config.URL_ADD_PROSES)
                .addBodyParameter("kd_proses",kd_proses)
                .addBodyParameter("jenis",jenis)
                .addBodyParameter("kd_transaksi",kd_transaksi)
                .addBodyParameter("proses_ke",proses_ke)
                .addBodyParameter("jam_balik",jam_balik)
                .addBodyParameter("tgl",tgl)
                .addBodyParameter("jumlah",jumlah)
                .addBodyParameter("nama_proses",nama_proses)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        if(s.equals("Berhasil Input")) {
                            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, InputFw2.class);
                            i.putExtra("kode_proses", kd_proses);
                            i.putExtra("gabah", "belum");
                            context.startActivity(i);
                        }else{
                            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        Toast.makeText(context,"Koneksi Gagal!",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addDetailProses(final String kk) {
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);

        AndroidNetworking.post(Config.URL_ADD_DETAIL_PROSES)
                .addBodyParameter("kd_proses",kd_proses)
                .addBodyParameter("last",last)
                .addBodyParameter("gabah",kk)
                .addBodyParameter("proses_ke",proses_ke)
                .addBodyParameter("jam_balik",jam_balik)
                .addBodyParameter("tgl",tgl)
                .addBodyParameter("jumlah",jumlah)
                .addBodyParameter("nama_proses",nama_proses)
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        addDetailProses(kk);
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
