package com.aci.agendacultural;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aci.agendacultural.api.ApiService;
import com.aci.agendacultural.api.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //variables necesarias para la llamada
    private EditText email;
    private EditText contrasenya;
    private Button iniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_login);

        //llamada a los botones
        email = findViewById(R.id.inicio_email);
        contrasenya = findViewById(R.id.inicio_contrasena);
        iniciarSesion = findViewById(R.id.boton_inicioses);

        iniciarSesion.setOnClickListener(v -> iniciarSesion());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
//Metodo iniciarSesion
    private void iniciarSesion() {
        String emailIntroducido = email.getText().toString().trim();
        String password = contrasenya.getText().toString();

        Map<String, String> datos = new HashMap<>();
        datos.put("email", emailIntroducido);
        datos.put("contrasenyaEscrita", password);

        //conectar con retrofit api
        RetrofitClient retrofitClient = new RetrofitClient();
        ApiService api = retrofitClient.getApi();

        Call<ResponseBody> llamada = api.checkUser(datos);

        // enviar llamada
        llamada.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    // abrir la pantalla principal
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else if (response.code() == 401) {

                    Toast.makeText(LoginActivity.this,
                            "Datos incorrectos",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("LOGIN", "Error de conexión", t);
                Toast.makeText(LoginActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}