package com.aci.agendacultural.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aci.agendacultural.R;
import com.aci.agendacultural.model.Salas;

import java.util.List;

public class SalasAdapter extends ArrayAdapter<Salas> {

    public SalasAdapter(@NonNull Context context, int resource, @NonNull List<Salas> listaSalas) {
        super(context, resource, listaSalas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) getContext().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_salas, parent, false);
        }

        Salas salas = getItem(position);
        if (salas != null) {
            TextView nombre = convertView.findViewById(R.id.nombreSalaFichaEv);
            nombre.setText(salas.getNombre());

            TextView direccion = convertView.findViewById(R.id.direccionEvento);
            direccion.setText(salas.getDireccion());

            TextView web = convertView.findViewById(R.id.webSala);
            web.setText(salas.getWeb());
        }

        return convertView;
    }
}
