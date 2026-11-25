package com.aci.agendacultural.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aci.agendacultural.R;
import com.aci.agendacultural.model.Eventos;

import java.util.List;


public class EventosAdapter extends ArrayAdapter<Eventos> { /*ArrayAdapter va a trabajar (contener) con objs de tipo Eventos (definido en model)
     desde donde se crea el adapter y para que pueda trabajar con los recursos (datos y xml)
     el constructor, así se podrá crear un nuevo adapter desde xej el EventosFragment */
    public EventosAdapter(@NonNull Context context, int resource, @NonNull List<Eventos> listaEventos) {
        // llama al constructor de la clase ArrayAdapter de la que EventosAdapter hereda e indica los parámetros de esta clase
        super(context, resource, listaEventos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // position = posición del elemento dentro de la lista, convertView= vista a reutilizar que más tarde se definirá con inflater parent=lista a la que pertenece el elemento
        if (convertView == null) {
            // si la vista está vacía se crea una usando el inflater (trayendo el item_eventos) pero si no está vacía (==null false) se reutiliza
            LayoutInflater layoutInflater =
                    (LayoutInflater) getContext().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_eventos, parent, false);
        }

        // se recupera el objeto Eventos para comprobar que existe y pintar los elementos del item_eventos
        Eventos eventos = getItem(position);
        if (eventos != null) {
            // si listaEventos no está vacía
            // el TextView que va a poblar el metodo getNombre en el xml de item_eventos
            TextView titulo = convertView.findViewById(R.id.tituloEventoFicha);
            titulo.setText(eventos.getNombre());

            TextView sala = convertView.findViewById(R.id.nombreSalaFichaEv);
            sala.setText(eventos.getSala().getNombre()); // para que aparezca el nombre de la sala y no el Id

            TextView fechaInicio = convertView.findViewById(R.id.diaEvento);
            fechaInicio.setText(eventos.getFechaInicio());

            TextView fechaFinal = convertView.findViewById(R.id.fechaFinalEv);
            TextView separador = convertView.findViewById(R.id.separadorFechas);
            if (eventos.getFechaFinal() != null) {
                fechaFinal.setText(eventos.getFechaFinal());
                fechaFinal.setVisibility(View.VISIBLE);
                separador.setVisibility(View.VISIBLE);
            } else {
                fechaFinal.setVisibility(View.GONE);
                separador.setVisibility(View.GONE);
            }

            TextView hora = convertView.findViewById(R.id.horaEvento);
            if (eventos.getHora() != null) {
                hora.setText(eventos.getHora());
                hora.setVisibility(View.VISIBLE);
            } else {
                hora.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}


