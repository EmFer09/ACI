package com.aci.agendacultural;

import android.media.quality.ActiveProcessingPicture;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aci.agendacultural.adapter.SalasAdapter;
import com.aci.agendacultural.api.ApiService;
import com.aci.agendacultural.api.RetrofitClient;
import com.aci.agendacultural.model.Salas;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalasFragment extends Fragment {
    private ListView lista_salas; // elemento en el xml
    private List<Salas> listaSalas; // lista de objetos de la clase Salas(model)
    private SalasAdapter adapter;
    private ApiService apiService;
    private MaterialButtonToggleGroup filtroCategorias;


    public SalasFragment() {
        // Required empty public constructor
    }

    private void cargarSalas(String categoria) {
        Call<List<Salas>> filtro = apiService.findSalasByCategoria(categoria);

        filtro.enqueue(new Callback<List<Salas>>() {
            @Override
            public void onResponse(Call<List<Salas>> call, Response<List<Salas>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaSalas.clear();
                    listaSalas.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Salas>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_salas, container, false); // aquí decimos que View es el fragment_salas porque el metodo onCreateView lo ha devuelto
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lista_salas = view.findViewById(R.id.lista_salas);
        listaSalas = new ArrayList<>();
        adapter = new SalasAdapter(requireContext(), R.layout.item_salas, listaSalas);
        lista_salas.setAdapter(adapter);

        filtroCategorias = view.findViewById(R.id.filtro_botones_pelis_expos);

        RetrofitClient retrofitClient = new RetrofitClient();
        apiService = retrofitClient.getApi();

        cargarSalas("exposiciones");
        filtroCategorias.addOnButtonCheckedListener((materialButtonToggleGroup, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.boton_expos) {
                    cargarSalas("exposiciones");
                } else if (checkedId == R.id.boton_pelis) {
                    cargarSalas("peliculas");
                }
            }
        });




    }
}