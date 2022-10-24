package com.example.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginCheck {
    private String uname;
    private String paswd;
    private Context context;
    private Retrofit retrofit;

    public LoginCheck(String uname, String paswd, Context context, Retrofit retrofit) {
        this.uname = uname;
        this.paswd = paswd;
        this.context = context;
        this.retrofit = retrofit;
    }

    public void login(){
        ProgressDialog loading = ProgressDialog.show(context, "Login...", "Mohon Tunggu...", false, false);
        ApiService apiEndpointService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiEndpointService.login(uname, paswd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null) {
                    Toast.makeText(context, "Selamat Datang", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainMenu.class);
                    try {
                        Config.username = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Username atau Password anda salah!", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
