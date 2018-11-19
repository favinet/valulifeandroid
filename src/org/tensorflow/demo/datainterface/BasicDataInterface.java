package org.tensorflow.demo.datainterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicDataInterface {
    public HttpService service;

    public BasicDataInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.googleapis.com").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        service = retrofit.create(HttpService.class);
    }
}
