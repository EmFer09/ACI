package com.aci.agendacultural;

import android.os.Bundle;

public class EventosActivity extends ActividadComun {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.eventos);
        menuDesplegable();
        ajustarInsets();

    }
}