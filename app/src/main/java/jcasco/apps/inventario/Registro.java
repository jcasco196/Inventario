package jcasco.apps.inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private EditText etDisplayName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;
    private Button btnRegistrarse;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private TextView tvEntrar;
    private Calendar calendar;
    private int year, month, day;
    private ProgressDialog progressDialog;
    private TextView cumple;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Perfil.class));
        }

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    System.out.println("");
                }
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();

        etDisplayName = (EditText) findViewById(R.id.displayName);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword2 = (EditText) findViewById(R.id.password2);
        etPassword = (EditText) findViewById(R.id.password);
        tvEntrar = (TextView) findViewById(R.id.entrar);
        cumple = (TextView) findViewById(R.id.cumple);
        btnRegistrarse = (Button) findViewById(R.id.registrarse);

        progressDialog = new ProgressDialog(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        btnRegistrarse.setOnClickListener(this);
        tvEntrar.setOnClickListener(this);
    }

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
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void registerUser(){

        final String displayName = etDisplayName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String aniversario = cumple.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        String password  = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(displayName)){
            Toast.makeText(this,"Porfavor ponga un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Porfavor ponga un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(aniversario)){
            Toast.makeText(this,"Porfavor ponga una fecha de nacimiento", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Porfavor ponga una Password", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this,"Porfavor introduzca una Password de más de 6 caracteres", Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(password2)){
            Toast.makeText(this,"Porfavor introduzca una Password identica", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registrando, espere por favor...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                            finish();
                            startActivity(new Intent(getApplicationContext(), Perfil.class));
                        }else{
                            Toast.makeText(Registro.this,"Registro Erroneo", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                });

    }

    private void onAuthSuccess(FirebaseUser user) {
        String email = etEmail.getText().toString();
        String nombre = etDisplayName.getText().toString();
        String nacimiento = cumple.getText().toString();
        mDatabase.child("users").child(user.getUid()).setValue(new User(user.getUid(), nombre, email, nacimiento));

        startActivity(new Intent(Registro.this, Perfil.class));
        finish();
    }

    @Override
    public void onClick(View view) {

        if(view == btnRegistrarse){
            registerUser();
        }

        if(view == tvEntrar){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}