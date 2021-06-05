package com.selatan.tanikopi;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Stok extends AppCompatActivity {

    private ArrayList<IsiStok> isiStoks,stokFw,stokH,stokN;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        getIsiStok();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FragmentTab rb = null;
            switch (position) {
                case 0:
                    if (stokFw!=null) {
                        rb = new FragmentTab(stokFw);
                    }
                    return rb;
                case 1:
                    if (stokH!=null) {
                        rb = new FragmentTab(stokH);
                    }
                    return rb;
                case 2:
                    if (stokN!=null) {
                        rb = new FragmentTab(stokN);
                    }
                    return rb;
                default:
                    if (stokFw!=null) {
                        rb = new FragmentTab(stokFw);
                    }
                    return rb;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FULLWASH";
                case 1:
                    return "HONEY";
                case 2:
                    return "NATURAL";
                default:
                    return "FULLWASH";

            }
        }
    }
    private void addIsiStok(String s){
        JSONObject jsonObject = null;
        isiStoks = new ArrayList<>();
        stokFw =new ArrayList<>();
        stokH =new ArrayList<>();
        stokN= new ArrayList<>();
        try {
            jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kode_proses = jo.getString("id_proses");
                String jumlah = jo.getString("jumlah_kg_masuk");
                String gabah = jo.getString("gabah_kering");
                String jenis = jo.getString("jenis_proses");
                IsiStok is = new IsiStok(kode_proses,jumlah,gabah,jenis);
                isiStoks.add(is);
//                System.out.println(is.toString());
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
        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
//        System.out.println(stokFw.get(0).toString()+"hehe");
//        System.out.println(stokH.get(0).toString()+"heha");

    }
    private void getIsiStok(){
        final ProgressDialog loading;
        loading = ProgressDialog.show(this, "Mengambil Data...", "Mohon Tunggu...", false, false);
//        loading.setCanceledOnTouchOutside(true);
        AndroidNetworking.post(Config.URL_GET_STOK)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        addIsiStok(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.dismiss();
                        getIsiStok();
                    }
                });
//        for (int i=0;i<isiStoks.size();i++){
//            if(isiStoks.get(i).getJenis().equals("fw")||isiStoks.get(i).getJenis().equals("FW")||isiStoks.get(i).getJenis().equals("fW")||isiStoks.get(i).getJenis().equals("Fw")){
//                stokFw.add(isiStoks.get(i));
//            }else if(isiStoks.get(i).getJenis().equals("h")||isiStoks.get(i).getJenis().equals("H")){
//                stokH.add(isiStoks.get(i));
//            }else if(isiStoks.get(i).getJenis().equals("h")||isiStoks.get(i).getJenis().equals("N")){
//                stokN.add(isiStoks.get(i));
//            }
//        }
//        System.out.println(stokFw.get(0).toString()+"hehe");
    }
}
