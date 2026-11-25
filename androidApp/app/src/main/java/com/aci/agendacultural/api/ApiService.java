package com.aci.agendacultural.api;

import com.aci.agendacultural.model.Eventos;
import com.aci.agendacultural.model.Salas;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;

// interfaz donde definir métodos que serán usados en las network transactions (los endpoints de la API)

public interface ApiService {

    //iniciar sesión
    @POST("api/usuario/login")
    Call<ResponseBody> checkUser(@Body Map<String, String> datos);

    @GET("api/eventos")
    public Call<List<Eventos>> findAll();

    //encontrar evento por id
    @GET("api/eventos/evento/{eventoId}")
    Call<Eventos> findById(@Path("eventoId") int eventoId);

    //filtrar eventos por categoría
    @GET("api/eventos/{categoria}")
    Call<List<Eventos>> findByCategoria(@Path("categoria") String categoria);

    @GET("api/salas/categoria/{categoria}")
    Call<List<Salas>> findSalasByCategoria(@Path("categoria") String categoria);

    //filtrar por sala (para el Dialog de fichaSala)
    @GET("api/eventos/sala/{salaId}")
    Call<List<Eventos>> findBySala(@Path("salaId") int salaId);

    // filtrar eventos por fecha
    @GET("api/eventos/fecha/{fechaIntroducida}")
    Call<List<Eventos>> findByFecha(@Path("fechaIntroducida") String fecha);

    //faltan los favoritos y los datos del perfil de usuario !!!!
}