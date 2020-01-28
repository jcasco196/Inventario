package com.example.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Perfil extends AppCompatActivity {

    private ImageView imagenPerfil;
    private Button guardar;
    private TextView nombre;
    private TextView email;
    private TextView fechaNacimiento;

    //IMAGEN
    Uri mediaUri;
    Uri downloaderUrl;
    private final int RC_IMAGE_PICK = 5677;
    protected DatabaseReference mDatabase;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        guardar = findViewById(R.id.guardar);
        email = findViewById(R.id.email);
        nombre = findViewById(R.id.nombre);
        imagenPerfil = findViewById(R.id.imagenPerfil);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar.setEnabled(false);
                if (mediaUri != null){
                    uploadFile();

                } else{
                    Toast.makeText(getApplicationContext(),"NO PUEDES GUARDAR NADA QUE NO HAYA SIDO SELECCIONADO.",Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(new Intent(Perfil.this, Perfil.class));
            }

        });

        imagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RC_IMAGE_PICK);
            }
        });
    } // FIN ONCREATE

    void uploadFile(){
        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("imagepP/" + UUID.randomUUID());
        fileRef.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloaderUrl = taskSnapshot.getDownloadUrl();
                subirImagen();
            }
        });
    }

    private void subirImagen() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(downloaderUrl == null) {
            mDatabase.child("imagenPersonal").child(user.getUid()).setValue(new ImagenPerfil(user.getUid(), null, null, null, null));
        } else {
            mDatabase.child("imagenPersonal").child(user.getUid()).setValue(new ImagenPerfil(user.getUid(), null, downloaderUrl.toString(), null, null));
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == RC_IMAGE_PICK) {
                mediaUri = data.getData();
                Glide.with(this).load(mediaUri).into(imagenPerfil);
            }
        }
    }

}