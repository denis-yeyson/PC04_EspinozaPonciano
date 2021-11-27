package com.denisyeyson.pc04_espinozaponciano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denisyeyson.pc04_espinozaponciano.adaptador.ListaProductosAdapter;
import com.denisyeyson.pc04_espinozaponciano.entidades.Productos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    DatabaseReference base_de_datos;
    List<Productos> listaProductos= new ArrayList<>();
    RecyclerView rvProductos;
    ListaProductosAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        base_de_datos = FirebaseDatabase.getInstance().getReference();
        rvProductos = findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new GridLayoutManager(this,1));
        obtenerProductos();

    }

    public void obtenerProductos(){
        listaProductos.clear();

        base_de_datos.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objeto: snapshot.getChildren()){
                    listaProductos.add(objeto.getValue(Productos.class));
                }
                adaptador = new ListaProductosAdapter(MainActivity2.this,listaProductos);
                rvProductos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}