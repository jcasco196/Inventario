package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Perfil extends AppCompatActivity {

    private Button editarPerfil;
    private ImageView imagenPerfil;
    private TextView nombre;
    private TextView email;
    private TextView fechaNacimiento;

    protected DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String uid = FirebaseAuth.getInstance().getUid();
        
        email = findViewById(R.id.email);
        nombre = findViewById(R.id.nombre);
        imagenPerfil = findViewById(R.id.imagenPerfil);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);
        editarPerfil = findViewById(R.id.editarPerfil);

        mDatabase.child("imagenPerfil").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImagenPerfil imagen_Perfil = dataSnapshot.getValue(ImagenPerfil.class);
                if (imagen_Perfil != null) {
                    imagenPerfil.setVisibility(View.VISIBLE);
                    Glide.with(Perfil.this)
                            .load(imagen_Perfil.imagenP2)
                            .into(imagenPerfil);
                } else {
                    imagenPerfil.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null) {
                    nombre.setText(user.displayName);
                    email.setText(user.email);
                    fechaNacimiento.setText(user.cumple);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Perfil.this, EditarPerfil.class);
                startActivity(i);
            }
        });


    } // FIN ONCREATE


}