package jcasco.apps.inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inventario.R;
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

public class CrearInventario extends AppCompatActivity {

    private EditText nombreInventario;
    private TextView fechaCreacion;
    private ImageView imagenInventario;
    private EditText descripcion;
    private Button guardar;
    private Calendar calendar;
    private int year, month, day;

    Uri mediaUri;
    Uri downloaderUrl;
    private final int RC_IMAGE_PICK = 5677;
    protected DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_inventario);

        nombreInventario = findViewById(R.id.nombreInventario);
        fechaCreacion = findViewById(R.id.fechaCreacion);
        imagenInventario = findViewById(R.id.imagenInventario);
        guardar = findViewById(R.id.guardar);
        descripcion = findViewById(R.id.descripcion);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String uid = FirebaseAuth.getInstance().getUid();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nombreInventario.getText().toString().trim())){
                    Toast.makeText(CrearInventario.this,"Porfavor ponga un nombre de Inventario", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(fechaCreacion.getText().toString().trim())){
                    Toast.makeText(CrearInventario.this,"Porfavor ponga una fecha de creación de Inventario", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(descripcion.getText().toString())){
                    Toast.makeText(CrearInventario.this,"Porfavor ponga una descripción del Inventario", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(descripcion.getText().toString())  || descripcion.getText().toString().length() < 30 || descripcion.getText().toString().length() > 70){
                    descripcion.setError("La descripción ha de tener mínimo 30 carácteres y máximo 70.");
                    return;
                }

                final ProgressDialog progressDialog = new ProgressDialog(CrearInventario.this);
                progressDialog.setIcon(R.mipmap.ic_launcher);
                progressDialog.setMessage("Cargando...");
                progressDialog.show();
                guardar.setEnabled(false);
                if (mediaUri != null){
                    uploadFile();
                } else{
                    crearInventario();
                }
                finish();
                startActivity(new Intent(CrearInventario.this, MisInventarios.class));
            }
        });
        imagenInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RC_IMAGE_PICK);
            }
        });
    }

    void uploadFile(){
        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("Inventarios/" +  UUID.randomUUID() + nombreInventario.getText());
        fileRef.putFile(mediaUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloaderUrl = taskSnapshot.getDownloadUrl();
                crearInventario();
            }
        });
    }



    private void crearInventario() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String nombreInventarioN = nombreInventario.getText().toString();
        String fecha = fechaCreacion.getText().toString();
        String desc = descripcion.getText().toString();
        if(downloaderUrl == null) {
            mDatabase.child("inventarios").child(user.getUid()).push().setValue(new Inventarios(user.getUid(), nombreInventarioN, fecha, desc, null));
        } else {
            mDatabase.child("inventarios").child(user.getUid()).push().setValue(new Inventarios(user.getUid(), nombreInventarioN, fecha, desc, downloaderUrl.toString()));
        }
        finish();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Veamos tu fecha de creación", Toast.LENGTH_SHORT)
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
        fechaCreacion.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == RC_IMAGE_PICK) {
                mediaUri = data.getData();
                Glide.with(this).load(mediaUri).into(imagenInventario);
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CrearInventario.this, MisInventarios.class));
        finish();
    }
}