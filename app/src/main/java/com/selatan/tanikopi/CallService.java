package com.selatan.tanikopi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.selatan.tanikopi.Config.BASE_URL;

public class CallService {
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = UnsafeOkHttp.getUnsafeOkHttpClient();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
