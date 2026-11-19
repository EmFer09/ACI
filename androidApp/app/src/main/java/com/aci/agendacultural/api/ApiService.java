package com.aci.agendacultural.api;

import com.aci.agendacultural.model.Eventos;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;
import okhttp3.ResponseBody;

public interface ApiService {

    //iniciar sesión
    @POST("api/usuario/login")
    Call<ResponseBody> checkUser(@Body Map<String, String> datos);

    @GET("api/eventos")
    public Call<List<Eventos>> findAll();

    //filtrar por id
    @GET("api/eventos/evento/{eventoId}")
    Call<Eventos> findById(@Path("eventoId") int eventoId);

    //filtrar por categoría
    @GET("api/eventos/{categoria}")
    Call<List<Eventos>> findByCategoria(@Path("categoria") String categoria);

    //filtrar por sala
    @GET("api/eventos/sala/{salaId}")
    Call<List<Eventos>> findBySala(@Path("salaId") int salaId);

    // filtrar eventos por fecha
    @GET("api/eventos/fecha/{fechaIntroducida}")
    Call<List<Eventos>> findByFecha(@Path("fechaIntroducida") String fecha);
}