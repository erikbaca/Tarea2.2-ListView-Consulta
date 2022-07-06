package com.example.Tarea2_2_Aplicacion_ListView_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Tarea2_2_Aplicacion_ListView_api.Interfaces.UsuariosApi;
import com.example.Tarea2_2_Aplicacion_ListView_api.Models.Usuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {

    ListView listacompleta;
    ArrayList<String> titulos = new ArrayList<>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        obtenerListaPosts();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
        listacompleta = (ListView) findViewById(R.id.listposts);
        listacompleta.setAdapter(arrayAdapter);
    }


    private void obtenerListaPosts()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/posts/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuariosApi usuariosApi = retrofit.create(UsuariosApi.class);

        Call<List<Usuarios>> calllista = usuariosApi.getUsuarios();

        calllista.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response)
            {
                for(Usuarios usuarios : response.body())
                {
                    Log.i(usuarios.getTitle(), "onResponse");
                    titulos.add(usuarios.getTitle());

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                t.getMessage();
            }
        });
    }

}