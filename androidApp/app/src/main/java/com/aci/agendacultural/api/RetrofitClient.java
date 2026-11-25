package com.aci.agendacultural.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// clase que se encarga de instanciar un objeto retrofit, objeto que hará posible las peticiones
public class RetrofitClient { //se crea un objeto retrofit para poder construir la HTTP request call

    private static final String BASE_URL= "http://10.0.2.2:8080/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService getApi() {
        return retrofit.create(ApiService.class); //para usar las anotaciones escritas en ApiService
    }

}