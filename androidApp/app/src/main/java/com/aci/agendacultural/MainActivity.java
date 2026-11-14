package com.aci.agendacultural;

import android.os.Bundle;

public class MainActivity extends ActividadComun {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        menuDesplegable();
        ajustarInsets();

    }
}