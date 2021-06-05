package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import java.util.ArrayList;

public class AdapterStok extends RecyclerView.Adapter<AdapterStok.AdapterStokViewHolder> {

    private ArrayList<IsiStok> dataList;
    private Context context;
    public AdapterStok(ArrayList<IsiStok> dataList,Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterStokViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_isi_stok, viewGroup, false);
        return new AdapterStokViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStokViewHolder adapterStokViewHolder, int i) {
        adapterStokViewHolder.txKodeProses.setText(dataList.get(i).getKodeProses());
        adapterStokViewHolder.txJumlahGreen.setText(dataList.get(i).getJumlahGreen());
        final String s = adapterStokViewHolder.txKodeProses.getText().toString().trim();
        if (adapterStokViewHolder.txJumlahGreen.getText().toString().equals("0")){
            adapterStokViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,InputFw2.class);
                    i.putExtra("kode_proses",s);
                    i.putExtra("gabah","belum");
                    context.startActivity(i);
                }
            });
        }else if (dataList.get(i).getGabah().equals("n")) {
            adapterStokViewHolder.bgchange.setBackgroundColor(Color.parseColor("#85bda6"));
            adapterStokViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
        }else{
            adapterStokViewHolder.bgchange.setBackgroundColor(Color.parseColor("#f3d55b"));
            adapterStokViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,InputFw2.class);
                    i.putExtra("kode_proses",s);
                    i.putExtra("gabah","kering");
                    context.startActivity(i);
                }
            });
        }
    }

    private void showDialog(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Mengambil Data...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);

        AndroidNetworking.post(Config.URL_GET_JUMLAH_STOK)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        if (s.isEmpty()){
                            Toast.makeText(context,"Data Kosong",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            new AlertDialog.Builder(context).setTitle("Jumlah Stok Gudang")
                                    .setMessage("Jumlah Stok Gudang = " + s)
                                    .setPositiveButton("Ya", null)
                                    .create().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        showDialog();
                    }
                });
    }
    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterStokViewHolder extends RecyclerView.ViewHolder{
        private TextView txKodeProses, txJumlahGreen;
        private LinearLayout bgchange;
        public AdapterStokViewHolder(View itemView) {
            super(itemView);
            txKodeProses = (TextView) itemView.findViewById(R.id.txKodeProses);
            txJumlahGreen = (TextView) itemView.findViewById(R.id.txJumlahGreen);
            bgchange = itemView.findViewById(R.id.bgchangestok);
        }
    }
}
