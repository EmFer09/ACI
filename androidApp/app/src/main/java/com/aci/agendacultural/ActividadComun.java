package com.aci.agendacultural;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActividadComun extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //para que la app no tenga el modo oscuro disponible
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
        );

        //para que la app use toda la pantalla de cada móvil
        EdgeToEdge.enable(this);
    }

    //Menú desplegable de la barra inferior
    public void menuDesplegable() {
        // menú desplegable
        ImageButton botonMenu = findViewById(R.id.boton_menu);
        botonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, botonMenu);
            popupMenu.getMenuInflater().inflate(
                    R.menu.menu_desplegable, popupMenu.getMenu()
            );
            popupMenu.setForceShowIcon(true);
            popupMenu.show();
        });
    }

    //para que la barra de hora, batería, notificaciones de Android no interfiera con la app
    public void ajustarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}