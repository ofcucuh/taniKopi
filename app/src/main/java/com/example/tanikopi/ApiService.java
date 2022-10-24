package com.example.tanikopi;

import static com.example.tanikopi.Config.URL_ADD_CHERRY;
import static com.example.tanikopi.Config.URL_ADD_DETAIL_PROSES;
import static com.example.tanikopi.Config.URL_ADD_PROSES;
import static com.example.tanikopi.Config.URL_GET_ID;
import static com.example.tanikopi.Config.URL_GET_JUMLAH_STOK;
import static com.example.tanikopi.Config.URL_GET_NAMA_PETANI;
import static com.example.tanikopi.Config.URL_GET_PROSES_KE;
import static com.example.tanikopi.Config.URL_GET_STOK;
import static com.example.tanikopi.Config.URL_LOGIN;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST(URL_LOGIN)
    Call<ResponseBody> login(
            @Field("username") String username,
            @Field("passwd") String passwd
    );
    @FormUrlEncoded
    @POST(URL_ADD_CHERRY)
    Call<String> addCherry(
            @Field("kd_transaksi") String kd_transaksi,
            @Field("id_petani") String id_petani,
            @Field("harga_cherry") String harga_cherry,
            @Field("jenis") String jenis,
            @Field("jumlah") String jumlah,
            @Field("tgl") String tgl,
            @Field("ketinggian") String ketinggian,
            @Field("varietas") String varietas
    );

    @FormUrlEncoded
    @POST(URL_ADD_PROSES)
    Call<String> addProses(
            @Field("kd_proses") String kd_proses,
            @Field("jenis") String jenis,
            @Field("kd_transaksi") String kd_transaksi,
            @Field("proses_ke") String proses_ke,
            @Field("jam_balik") String jam_balik,
            @Field("tgl") String tgl,
            @Field("jumlah") String jumlah,
            @Field("nama_proses") String nama_proses
    );

    @FormUrlEncoded
    @POST(URL_ADD_DETAIL_PROSES)
    Call<String> addDetailProses(
            @Field("kd_proses") String kd_proses,
            @Field("last") String last,
            @Field("gabah") String gabah,
            @Field("proses_ke") String proses_ke,
            @Field("jam_balik") String jam_balik,
            @Field("tgl") String tgl,
            @Field("jumlah") String jumlah,
            @Field("nama_proses") String nama_proses
    );

    @FormUrlEncoded
    @POST(URL_GET_NAMA_PETANI)
    Call<String> getNamaPetani(@Field("id_petani") String id_petani);

    @FormUrlEncoded
    @POST(URL_GET_PROSES_KE)
    Call<String> getProsesKe(@Field("kd_proses") String kd_proses);

//    @FormUrlEncoded
    @POST(URL_GET_ID)
    Call<String> getId();

//    @FormUrlEncoded
    @POST(URL_GET_JUMLAH_STOK)
    Call<String> getJumlahStok();

//    @FormUrlEncoded
    @POST(URL_GET_STOK)
    Call<ResponseBody> getStok();
}
