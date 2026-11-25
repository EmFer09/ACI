package com.aci.agendacultural;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aci.agendacultural.adapter.EventosAdapter;
import com.aci.agendacultural.api.ApiService;
import com.aci.agendacultural.api.RetrofitClient;
import com.aci.agendacultural.model.Eventos;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventosFragment extends Fragment {

    private ListView lista_eventos; // donde se va a mostrar la lista de eventos
    private List<Eventos> listaEventos; // almacena los objetos Eventos que encuentra Retrofit
    private EventosAdapter adapter; // para almacenar esos objetos en los item_eventos
    private ApiService apiService; // para hacer peticiones a la API
    private MaterialButtonToggleGroup filtroCategorias; // para conseguir los botones que activan el filtro por categoría

    public EventosFragment() {
        // Required empty public constructor
    }

    // creo un metodo para hacer la llamada a la api, así no hay que repetir código para cuando se seleccione exposiciones o películas
    private void cargarEventos(String categoria) {
        // se pide a la API los eventos filtrados por categoría primero creando un objeto llamado filtro que representa la petición HTTP
        Call<List<Eventos>> filtro = apiService.findByCategoria(categoria);

        filtro.enqueue(new Callback<List<Eventos>>() { //enqueue() manda la petición al servidor de forma asíncrona
            @Override //si va bien
            public void onResponse(Call<List<Eventos>> call, Response<List<Eventos>> response) {
                // cuando encuentre los eventos hay que limpiar la lista antes creada para poblarla con lo encontrado
                if (response.isSuccessful() && response.body() != null) {
                    listaEventos.clear();
                    listaEventos.addAll(response.body());
                    adapter.notifyDataSetChanged(); // avisa al adapter que hay datos nuevos y debe actualizar la vista
                }
            }
            @Override // si la petición falla
            public void onFailure(Call<List<Eventos>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, y lo guardo en una variable view para luego acceder al elemento correspondiente a la lista
        View vista = inflater.inflate(R.layout.fragment_eventos, container, false);
        lista_eventos = vista.findViewById(R.id.lista_eventos);
        filtroCategorias = vista.findViewById(R.id.filtro_botones_pelis_expos);

        listaEventos = new ArrayList<>(); // se inicializa una lista vacía que almacenará los objs recibidos por la API
        adapter = new EventosAdapter(requireContext(), R.layout.item_eventos, listaEventos); // se llama al adapter creado en EventosAdapter
        lista_eventos.setAdapter(adapter); // asocio el adapter al ListView (elemento xml) para mostrar los objetos de listaEventos

        // ahora se carga retrofit
        RetrofitClient retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getApi();

        cargarEventos("expo"); // para que by default se carguen las exposiciones y luego al pulsar el botón cambie la lista:
        filtroCategorias.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.boton_expos) {
                    cargarEventos("expo");
                } else if (checkedId == R.id.boton_pelis) { // luego arreglar pelis xq sale fatal, mil veces la misma peli etc
                    cargarEventos("película");
                }
            }
        });

        return vista;


    }
}