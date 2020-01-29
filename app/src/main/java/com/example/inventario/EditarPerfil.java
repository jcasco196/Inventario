package com.example.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.UUID;

public class EditarPerfil extends AppCompatActivity {

    private EditText nombre;
    private EditText email;
    private TextView cumple;
    private Calendar calendar;
    private int year, month, day;

    //IMAGEN
    Uri mediaUri;
    Uri downloaderUrl;
    private final int RC_IMAGE_PICK = 5677;
    protected DatabaseReference mDatabase;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        nombre = findViewById(R.id.nombre);
        cumple = findViewById(R.id.cumple);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        guardar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                email.setEnabled(false);
//                if (mediaUri != null){
//                    uploadFile();
//
//                } else{
//                    Toast.makeText(getApplicationContext(),"NO PUEDES GUARDAR NADA QUE NO HAYA SIDO SELECCIONADO.",Toast.LENGTH_SHORT).show();
//                }
//                finish();
//                startActivity(new Intent(EditarPerfil.this, Perfil.class));
//            }
//
//        });
//
//        imagenPerfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent,RC_IMAGE_PICK);
//            }
//        });
    } // FIN ONCREATE

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Veamos tu fecha", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        cumple.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


//    void uploadFile(){
//        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("imagenPerfil/" + UUID.randomUUID());
//        fileRef.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                downloaderUrl = taskSnapshot.getDownloadUrl();
//                subirImagen();
//            }
//        });
//    }
//
//    private void subirImagen() {
//
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(downloaderUrl == null) {
//            mDatabase.child("imagenPerfil").child(user.getUid()).setValue(new ImagenPerfil(user.getUid(), null, null, null, null));
//        } else {
//            mDatabase.child("imagenPerfil").child(user.getUid()).setValue(new ImagenPerfil(user.getUid(), null, downloaderUrl.toString(), null, null));
//        }
//        finish();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            if (requestCode == RC_IMAGE_PICK) {
//                mediaUri = data.getData();
//                Glide.with(this).load(mediaUri).into(imagenPerfil);
//            }
//        }
//    }
}
