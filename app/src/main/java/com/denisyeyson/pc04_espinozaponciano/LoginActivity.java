package com.denisyeyson.pc04_espinozaponciano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText nombre, correo, clave;
    Button registro, ingresar;

    ProgressBar progressBar;

    String name,email, password; // Estas variables van a contener lo del correo y clave

    FirebaseAuth autentificacion;
    DatabaseReference base_de_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autentificacion = FirebaseAuth.getInstance();
        base_de_datos = FirebaseDatabase.getInstance().getReference();


        correo = findViewById(R.id.txt_email);
        clave = findViewById(R.id.txt_clave);
        progressBar = findViewById(R.id.loading);
        ingresar = findViewById(R.id.btn_iniciar_sesion);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = correo.getText().toString().trim();
                password = clave.getText().toString().trim();

                if(email.length() > 0 && password.length() > 0){
                    IniciarSesion(email, password);
                }else{
                    Toast.makeText(LoginActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void IniciarSesion(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        autentificacion.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            LimpiarCajasDeTextos();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Usuario a iniciado sesion", Toast.LENGTH_SHORT).show();

                            //Nos llevará al segundo activity
                            Intent intent = new Intent(LoginActivity.this, RegistroProductoActivity.class);
                            startActivity(intent);
                        }else{
                            LimpiarCajasDeTextos();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Email y/o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                LimpiarCajasDeTextos();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "ERROR: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void LimpiarCajasDeTextos(){
        clave.setText("");
        correo.setText("");
    }
}