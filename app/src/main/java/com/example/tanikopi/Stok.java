package com.example.tanikopi;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Stok extends AppCompatActivity {

    private ArrayList<IsiStok> isiStoks,stokFw,stokH,stokN;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Retrofit retrofit;
    private ApiService apiEndpointService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        retrofit = CallService.getClient();
        apiEndpointService = retrofit.create(ApiService.class);

        getIsiStok();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


//    public class SectionPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
////        @Override
////        public Fragment getItem(int position) {
////            FragmentTab rb = null;
////            switch (position) {
////                case 0:
////                    if (stokFw!=null) {
////                        rb = new FragmentTab(stokFw);
////                    }
////                    return rb;
////                case 1:
////                    if (stokH!=null) {
////                        rb = new FragmentTab(stokH);
////                    }
////                    return rb;
////                case 2:
////                    if (stokN!=null) {
////                        rb = new FragmentTab(stokN);
////                    }
////                    return rb;
////                default:
////                    if (stokFw!=null) {
////                        rb = new FragmentTab(stokFw);
////                    }
////                    return rb;
////            }
////        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return "FULLWASH";
//                case 1:
//                    return "HONEY";
//                case 2:
//                    return "NATURAL";
//                default:
//                    return "FULLWASH";
//
//            }
//        }
//    }
    private void addIsiStok(String s){
        JSONObject jsonObject = null;
        isiStoks = new ArrayList<>();
        stokFw =new ArrayList<>();
        stokH =new ArrayList<>();
        stokN= new ArrayList<>();
        try {
            jsonObject = new JSONObject(s);
            System.out.println(s);
            System.out.println(jsonObject.toString());
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kode_proses = jo.getString("id_proses");
                String jumlah = jo.getString("jumlah_kg_masuk");
                String gabah = jo.getString("gabah_kering");
                String jenis = jo.getString("jenis_proses");
                IsiStok is = new IsiStok(kode_proses,jumlah,gabah,jenis);
                isiStoks.add(is);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0;i<isiStoks.size();i++){
            if(isiStoks.get(i).getJenis().equals("fw")||isiStoks.get(i).getJenis().equals("FW")||isiStoks.get(i).getJenis().equals("fW")||isiStoks.get(i).getJenis().equals("Fw")){
                stokFw.add(isiStoks.get(i));
            }else if(isiStoks.get(i).getJenis().equals("h")||isiStoks.get(i).getJenis().equals("H")){
                stokH.add(isiStoks.get(i));
            }else if(isiStoks.get(i).getJenis().equals("n")||isiStoks.get(i).getJenis().equals("N")){
                stokN.add(isiStoks.get(i));
            }
        }
//        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }
    private void getIsiStok(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(this, "Mengambil Data...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);

        Call<ResponseBody> call = apiEndpointService.getStok();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                addIsiStok(response.body());
                loading.dismiss();
                try {
                    addIsiStok(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Stok.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });

//        System.out.println(stokFw.get(0).toString()+"hehe");
    }
}
