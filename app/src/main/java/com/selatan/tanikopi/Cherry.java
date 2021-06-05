package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cherry {
    private Context context;
    private String kd_transaksi,id_petani,jenis,varietas;
    private Date tgl;
    private Double jumlah,ketinggian,harga;

    public Cherry(Context context, String kd_transaksi, String id_petani, String jenis, Date tgl, Double jumlah, Double ketinggian, Double harga, String varietas) {
        this.context = context;
        this.kd_transaksi = kd_transaksi;
        this.id_petani = id_petani;
        this.jenis = jenis;
        this.tgl = tgl;
        this.jumlah = jumlah;
        this.ketinggian = ketinggian;
        this.harga = harga;
        this.varietas = varietas;
    }
    public Cherry(String id_petani,Context context){
        this.context=context;
        this.id_petani=id_petani;
    }
    public void addCherry(){
        final ProgressDialog loading;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(tgl);
        loading = ProgressDialog.show(context, "Menambahkan...", "Mohon Tunggu...", false, true);
        loading.setCanceledOnTouchOutside(true);
        AndroidNetworking.post(Config.URL_ADD_CHERRY)
                .addBodyParameter("kd_transaksi", kd_transaksi)
                .addBodyParameter("id_petani", id_petani)
                .addBodyParameter("harga_cherry",String.valueOf(harga))
                .addBodyParameter("jenis",jenis)
                .addBodyParameter("jumlah",String.valueOf(jumlah))
                .addBodyParameter("tgl",date)
                .addBodyParameter("ketinggian",String.valueOf(ketinggian))
                .addBodyParameter("varietas",varietas)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        if (s.equals("")) {
                            Toast.makeText(context, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
                        } else {
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

    public void setNamaPetani(final EditText tx){
        AndroidNetworking.post(Config.URL_GET_NAMA_PETANI)
                .addBodyParameter("id_petani", id_petani)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
//                        loading.dismiss();
//                        if (s.equals("")) {
//                            Toast.makeText(context, "Koneksi Gagal!", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
//                        }
                        if(!s.equals("")) {
                            tx.setText(s);
                        }else{
                            tx.setText("Nama Petani Tidak Ditemukan!");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        loading.dismiss();
                        tx.setText("Nama Petani Tidak Ditemukan!");
                    }
                });
    }
}
