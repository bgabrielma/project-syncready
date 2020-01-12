package com.example.syncreadyapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static boolean devMode = false;
    private static String port = "3000";
    public static String BASE_URL = devMode ? "http://10.0.3.2" + ":" + port + "/" : "http://157.230.96.205" + ":" + port + "/";

    public static SyncReadyMobileDataService getService() {
        if (retrofit == null) {

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SyncReadyMobileDataService.class);
    }
}
