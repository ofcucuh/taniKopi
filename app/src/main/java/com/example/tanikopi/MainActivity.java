package com.example.tanikopi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    EditText pswd,user;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = CallService.getClient();

        pswd = findViewById(R.id.editPasswd);
        user = findViewById(R.id.editUser);
        Button clickButton = (Button) findViewById(R.id.loginBtn);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }
    private void login(){
        LoginCheck lc = new LoginCheck(user.getText().toString(),pswd.getText().toString(),this,retrofit);
        lc.login();
//        Intent i=new Intent(this,MainMenu.class);
//        startActivity(i);

    }
    public void onBackPressed(){
        new AlertDialog.Builder(this).setTitle("Anda Yakin?")
                .setMessage("Apakah anda yakin untuk keluar?")
                .setNegativeButton("Tidak",null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                }).create().show();
    }
}
