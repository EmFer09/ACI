package com.aci.agendacultural;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //para que la app no tenga el modo oscuro disponible
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        //para que la app use toda la pantalla de cada móvil
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //para que la barra de hora, batería, notificaciones de Android no interfiera con la app
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Menú desplegable de la barra inferior
        menuDesplegable();

        //Fragment inicial
        if (savedInstanceState == null) {
            mostrar(new HomeFragment());
        }
    }
    //menú desplegable visible en todas las pantallas
    public void menuDesplegable() {
        ImageButton botonMenu = findViewById(R.id.boton_menu);
        botonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, botonMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_desplegable, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);

            //qué fragment corresponde a cada opción del menú
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.desplegable_eventos) {
                    mostrar(new EventosFragment());
                } else if (id == R.id.desplegable_calendario) {
                    mostrar(new CalendarioFragment());
                } else if (id == R.id.desplegable_favs) {
                    mostrar(new FavsFragment());
                } else if (id == R.id.desplegable_salas) {
                    mostrar(new SalasFragment());
                } else if (id == R.id.desplegable_perfil) {
                    mostrar(new PerfilFragment());
                } else if (id == R.id.desplegable_home) {
                    mostrar(new HomeFragment());
                } else {
                    return false;
                }
                return true;
            });
            popupMenu.show();
        });
    }

    public void mostrar(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_fragments, fragment)
                .commit();
    }

}

