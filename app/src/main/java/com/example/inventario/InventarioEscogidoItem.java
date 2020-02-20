package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InventarioEscogidoItem extends AppCompatActivity {

    private TextView nombreInventario, fechaCreacion, descripcion;
    private ImageView image;
    String idInventario;
    private DatabaseReference mDatabase;
//    private FirebaseRecyclerAdapter mAdapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_escogido_item);

        nombreInventario = findViewById(R.id.nombreInventario);
        fechaCreacion = findViewById(R.id.fechaCreacion);
        descripcion = findViewById(R.id.descripcion);
        image = findViewById(R.id.image);

        idInventario = getIntent().getStringExtra("INVENTARIO_KEY");
        String uid = FirebaseAuth.getInstance().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("inventarios").child(uid);

      //  Query postsQuery = mDatabase;

        datosInventario();

    }

    void datosInventario(){
        mDatabase.child(idInventario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Inventarios inventarios = dataSnapshot.getValue(Inventarios.class);
                if (inventarios != null){
                    nombreInventario.setText(inventarios.displayNameInventarios);
                    fechaCreacion.setText(inventarios.date);
                    descripcion.setText(inventarios.descripcionInventarios);
                    Glide.with(InventarioEscogidoItem.this)
                            .load(inventarios.photoInventariosUrl)
                            .into(image);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
