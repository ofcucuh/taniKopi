package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;


public class LoginCheck {
    private String uname;
    private String paswd;
    private Context context;

    public LoginCheck(String uname, String paswd, Context context) {
        this.uname = uname;
        this.paswd = paswd;
        this.context = context;
    }

    public void login(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(context, "Login...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);
        AndroidNetworking.post(Config.URL_LOGIN)
                .addBodyParameter("username",uname)
                .addBodyParameter("passwd", paswd)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        if (s.equals("Username atau password salah !")) {
                            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Selamat Datang", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, MainMenu.class);
                            Config.username = s;
                            context.startActivity(i);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        login();
                    }
                });
    }
}
