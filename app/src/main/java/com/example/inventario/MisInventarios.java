package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MisInventarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_inventarios);

        agregarFAB();

    }

    //Metodo que añade el FAB
    public void agregarFAB(){
        //Inicializamos el FAB
        FloatingActionButton miFAB = (FloatingActionButton) findViewById(R.id.fab);
        //Creamos el evento setOnClickListener para que se ejecute una accion al pulsar el FAB.
        miFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Codigo que se ejecuta al pulsar el FAB.
                Toast.makeText(MisInventarios.this, "Boton Pulsado", Toast.LENGTH_SHORT).show();
                //En este caso mostrara un Toast con el mensaje "Boton pulsado".
            }
        });
    }
}
