package com.denisyeyson.pc04_espinozaponciano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class RegistroProductoActivity extends AppCompatActivity {

    EditText txtNombre,txtMarca,txtDescripcion,txtCantidad,txtPrecio;
    Button btnAgregarProducto;

    String nombre,marca,descripcion,cantidad,precio;

    FirebaseAuth autentificacion;
    DatabaseReference base_de_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        txtNombre = findViewById(R.id.txtNombres);
        txtMarca = findViewById(R.id.txtMarca);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = txtNombre.getText().toString().trim();
                marca = txtMarca.getText().toString().trim();
                descripcion = txtDescripcion.getText().toString().trim();
                cantidad = txtCantidad.getText().toString().trim();
                precio = txtPrecio.getText().toString().trim();

                if (nombre.isEmpty() || marca.isEmpty() || descripcion.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
                    Toast.makeText(RegistroProductoActivity.this, "Debe completar todos los campos para registrar", Toast.LENGTH_SHORT).show();
                } else {
                    registrarProducto(nombre, marca, descripcion, cantidad, precio);
                }
            }
        });
    }

    public void registrarProducto(String nombre, String marca,String descripcion,String cantidad,String precio){
       Map<String,Object> map = new HashMap<>();
            map.put("nombre",nombre);
            map.put("marca",marca);
            map.put("descripcion",descripcion);
            map.put("cantidad",cantidad);
            map.put("pecio",precio);
            base_de_datos.child("Productos").child(nombre).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task2) {
                    if(task2.isSuccessful()){
                        Toast.makeText(RegistroProductoActivity.this, "ProductoAgregado correctamente", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegistroProductoActivity.this,MainActivity2.class));
                        finish();
                    }else{
                        Toast.makeText(RegistroProductoActivity.this, "No se pudo guardar los datos en la Base de Datos", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
    public void limpiar(){
        txtNombre.setText("");
        txtMarca.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
    }
}