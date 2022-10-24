package com.example.tanikopi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterStok extends RecyclerView.Adapter<AdapterStok.AdapterStokViewHolder> {

    private ArrayList<IsiStok> dataList;
    private Context context;
    private Retrofit retrofit;

    public AdapterStok(ArrayList<IsiStok> dataList,Context context){
        this.dataList = dataList;
        this.context = context;
        retrofit = CallService.getClient();
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
        ApiService apiEndpointService = retrofit.create(ApiService.class);
        Call<String> call = apiEndpointService.getJumlahStok();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().isEmpty()){
                    Toast.makeText(context,"Data Kosong",Toast.LENGTH_SHORT).show();
                }else{
                    new AlertDialog.Builder(context).setTitle("Jumlah Stok Gudang")
                            .setMessage("Jumlah Stok Gudang = " + response.body())
                            .setPositiveButton("Ya", null)
                            .create().show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
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
